/*
 *
 * @author Ming-Jheng Li
 *
 *
 *         Copyright 2016 Ming-Jheng Li
 *
 *         Licensed under the Apache License, Version 2.0 (the "License"); you
 *         may not use this file except in compliance with the License. You may
 *         obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *         Unless required by applicable law or agreed to in writing, software
 *         distributed under the License is distributed on an "AS IS" BASIS,
 *         WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *         implied. See the License for the specific language governing
 *         permissions and limitations under the License.
 *
 */
package tw.edu.ym.lab525.controller;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.collect.Lists.newArrayList;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.common.io.Resources;

import tw.edu.ym.lab525.entity.Patient;
import tw.edu.ym.lab525.repository.PatientRepository;

@Controller
public class SetupController {

  @Autowired
  PatientRepository patientRepo;

  @PostConstruct
  void postProcessData() throws IOException {
    List<Patient> patients = newArrayList();
    Resources.readLines(Resources.getResource("setup.csv"), UTF_8)
        .forEach(line -> {
          String[] columns = line.split(",");
          if (patientRepo.findBySsid(columns[0]) == null) {
            Patient patient = new Patient(columns[0], columns[1], columns[2],
                columns[3], columns[4], columns[5]);
            patients.add(patient);
          }
        });
    patientRepo.save(patients);
  }

}
