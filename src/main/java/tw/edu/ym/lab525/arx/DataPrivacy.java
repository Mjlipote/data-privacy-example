/*
*
* Copyright 2016 Ming-Jheng Li
*
* Licensed under the Apache License, Version 2.0 (the "License"); you may not
* use this file except in compliance with the License. You may obtain a copy of
* the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
* WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
* License for the specific language governing permissions and limitations under
* the License.
*
*/
package tw.edu.ym.lab525.arx;

import static com.google.common.base.Charsets.UTF_8;

import java.io.IOException;
import java.util.List;

import org.deidentifier.arx.ARXAnonymizer;
import org.deidentifier.arx.ARXConfiguration;
import org.deidentifier.arx.ARXResult;
import org.deidentifier.arx.AttributeType;
import org.deidentifier.arx.AttributeType.Hierarchy;
import org.deidentifier.arx.AttributeType.Hierarchy.DefaultHierarchy;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.Data.DefaultData;
import org.deidentifier.arx.criteria.DistinctLDiversity;
import org.deidentifier.arx.criteria.HierarchicalDistanceTCloseness;
import org.deidentifier.arx.criteria.KAnonymity;
import org.deidentifier.arx.metric.Metric;

import com.google.common.io.Resources;

import tw.edu.ym.lab525.entity.Patient;

public class DataPrivacy {

  private final DefaultData data = Data.create();
  private final DefaultHierarchy age = Hierarchy.create();
  private final DefaultHierarchy nationality = Hierarchy.create();
  private final DefaultHierarchy gender = Hierarchy.create();
  private final DefaultHierarchy disease = Hierarchy.create();

  public DataPrivacy(List<Patient> patients) throws IOException {
    data.add("ssid", "name", "gender", "nationality", "age", "disease");
    for (Patient patient : patients) {
      data.add(patient.getSsid(), patient.getName(), patient.getGender(),
          patient.getNationality(), String.valueOf(patient.getAge()),
          patient.getDisease());
    }

    Resources.readLines(Resources.getResource("age.csv"), UTF_8)
        .forEach(line -> {
          String[] columns = line.split(",");
          age.add(columns[0], columns[1], columns[2], columns[3]);
        });

    Resources.readLines(Resources.getResource("nationality.csv"), UTF_8)
        .forEach(line -> {
          String[] columns = line.split(",");
          nationality.add(columns[0], columns[1], columns[2], columns[3]);
        });

    Resources.readLines(Resources.getResource("gender.csv"), UTF_8)
        .forEach(line -> {
          String[] columns = line.split(",");
          gender.add(columns[0], columns[1]);
        });

    Resources.readLines(Resources.getResource("disease.csv"), UTF_8)
        .forEach(line -> {
          String[] columns = line.split(",");
          disease.add(columns[0], columns[1], columns[2], columns[3]);
        });

    data.getDefinition().setAttributeType("ssid",
        AttributeType.IDENTIFYING_ATTRIBUTE);
    data.getDefinition().setAttributeType("name",
        AttributeType.IDENTIFYING_ATTRIBUTE);
    data.getDefinition().setAttributeType("gender", gender);
    data.getDefinition().setAttributeType("age", age);
    data.getDefinition().setAttributeType("nationality", nationality);
    data.getDefinition().setAttributeType("disease",
        AttributeType.SENSITIVE_ATTRIBUTE);

  }

  public ARXResult getARXResult(Integer k, Integer l) throws IOException {
    ARXAnonymizer anonymizer = new ARXAnonymizer();
    ARXConfiguration config = ARXConfiguration.create();
    config.addCriterion(new KAnonymity(k));
    config.addCriterion(new DistinctLDiversity("disease", l));
    config.addCriterion(
        new HierarchicalDistanceTCloseness("disease", 0.7d, disease));
    config.setMaxOutliers(0.7d);
    config.setMetric(Metric.createEntropyMetric());
    return anonymizer.anonymize(data, config);
  }

}
