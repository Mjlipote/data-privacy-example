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

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import tw.edu.ym.lab525.entity.Patient;
import tw.edu.ym.lab525.repository.PatientRepository;

@Controller
public class SetupController {

  @Autowired
  PatientRepository patientRepo;

  @PostConstruct
  void postProcessData() {

    List<Patient> patients = newArrayList();
    if (patientRepo.findBySsid("A123456789") == null) {
      Patient patient =
          new Patient("A123456789", "王小民", "M", "流感", "1999/01/01", "TAIWAN");
      patients.add(patient);
    }
    if (patientRepo.findBySsid("A121389490") == null) {
      Patient patient =
          new Patient("A121389490", "林大華", "M", "肺結核", "1956/01/08", "TAIWAN");
      patients.add(patient);
    }
    if (patientRepo.findBySsid("B275895862") == null) {
      Patient patient =
          new Patient("B275895862", "黃怡華", "F", "肺結核", "2000/11/01", "TAIWAN");
      patients.add(patient);
    }
    if (patientRepo.findBySsid("N263987444") == null) {
      Patient patient =
          new Patient("N263987444", "林怡君", "F", "肺結核", "1999/05/01", "TAIWAN");
      patients.add(patient);
    }
    if (patientRepo.findBySsid("J123456789") == null) {
      Patient patient =
          new Patient("J123456789", "小林優美", "F", "肺結核", "1960/12/01", "JAPAN");
      patients.add(patient);
    }
    if (patientRepo.findBySsid("USA123456789") == null) {
      Patient patient = new Patient("USA123456789", "Jack Bauer", "M", "流感",
          "1959/08/08", "USA");
      patients.add(patient);
    }
    if (patientRepo.findBySsid("K123456789") == null) {
      Patient patient =
          new Patient("K123456789", "金善美", "F", "胃潰瘍", "1966/07/01", "KOREA");
      patients.add(patient);
    }
    if (patientRepo.findBySsid("342965198001015479") == null) {
      Patient patient = new Patient("342965198001015479", "張偉", "M", "胃潰瘍",
          "1992/01/01", "CHINA");
      patients.add(patient);
    }
    if (patientRepo.findBySsid("F123456789") == null) {
      Patient patient = new Patient("F123456789", "Jacqueiline Martin", "F",
          "流感", "1977/01/01", "FRANCE");
      patients.add(patient);
    }
    if (patientRepo.findBySsid("U123456789") == null) {
      Patient patient = new Patient("U123456789", "Winston Churchill", "M",
          "胃潰瘍", "1971/01/01", "UK");
      patients.add(patient);
    }
    patientRepo.save(patients);
  }

}
