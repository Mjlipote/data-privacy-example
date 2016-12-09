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

    if (patientRepo.findBySsid("A123456789") == null) {
      Patient patient = new Patient();
      patient.setSsid("A123456789");
      patient.setName("王小明");
      patient.setAge(20);
      patient.setDisease("流感");
      patient.setGender("M");
      patient.setZipcode(12345);
      patientRepo.save(patient);
    }
  }

}
