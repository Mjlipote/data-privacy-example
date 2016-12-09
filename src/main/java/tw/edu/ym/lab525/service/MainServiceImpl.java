/*
 *
 * @author Ming-Jheng Li
 *
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
package tw.edu.ym.lab525.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import tw.edu.ym.lab525.entity.Patient;
import tw.edu.ym.lab525.repository.PatientRepository;

public class MainServiceImpl implements MainService {

  @Autowired
  private PatientRepository patientRepo;

  @Override
  public List<Patient> view() {
    return patientRepo.findAll();
  }

  @Override
  public List<Patient> lookup(String ssid) {
    return ssid.equals("") ? patientRepo.findAll()
        : Arrays.asList(patientRepo.findBySsid(ssid));
  }

  @Override
  public void create(String ssid, String name, String gender, String disease,
      Integer zipcode, Integer age) {
    Patient patient = new Patient();
    patient.setSsid(ssid);
    patient.setName(name);
    patient.setAge(age);
    patient.setDisease(disease);
    patient.setGender(gender);
    patient.setZipcode(zipcode);
    patientRepo.save(patient);
  }

  @Override
  public Patient read(String ssid) {
    return patientRepo.findBySsid(ssid);
  }

  @Override
  public void update(String ssid, String disease) {
    Patient patient = patientRepo.findBySsid(ssid);
    patient.setDisease(disease);
    patientRepo.saveAndFlush(patient);
  }

  @Override
  public void delete(String ssid) {
    patientRepo.delete(patientRepo.findBySsid(ssid));
  }

}
