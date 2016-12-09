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
package tw.edu.ym.lab525.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

@Entity
public class Patient extends AbstractPersistable<Long>
    implements Comparable<Patient> {

  private static final long serialVersionUID = 1L;

  public Patient() {}

  public Patient(String ssid, String name, String gender, String disease,
      Integer zipcode, Integer age) {
    this.ssid = ssid;
    this.name = name;
    this.gender = gender;
    this.disease = disease;
    this.zipcode = zipcode;
    this.age = age;

  }

  @Column(unique = true, nullable = false)
  private String ssid;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String gender;

  @Column(nullable = false)
  private String disease;

  @Column(nullable = false)
  private Integer zipcode;

  @Column(nullable = false)
  private Integer age;

  /**
   * @return the ssid
   */
  public String getSsid() {
    return ssid;
  }

  /**
   * @param ssid
   *          the ssid to set
   */
  public void setSsid(String ssid) {
    this.ssid = ssid;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the gender
   */
  public String getGender() {
    return gender;
  }

  /**
   * @param gender
   *          the gender to set
   */
  public void setGender(String gender) {
    this.gender = gender;
  }

  /**
   * @return the disease
   */
  public String getDisease() {
    return disease;
  }

  /**
   * @param disease
   *          the disease to set
   */
  public void setDisease(String disease) {
    this.disease = disease;
  }

  /**
   * @return the zipcode
   */
  public Integer getZipcode() {
    return zipcode;
  }

  /**
   * @param zipcode
   *          the zipcode to set
   */
  public void setZipcode(Integer zipcode) {
    this.zipcode = zipcode;
  }

  /**
   * @return the age
   */
  public Integer getAge() {
    return age;
  }

  /**
   * @param age
   *          the age to set
   */
  public void setAge(Integer age) {
    this.age = age;
  }

  @Override
  public int compareTo(final Patient other) {
    return ComparisonChain.start().compare(ssid, other.ssid)
        .compare(name, other.name).compare(gender, other.gender)
        .compare(disease, other.disease).compare(zipcode, other.zipcode)
        .compare(age, other.age).result();
  }

  @Override
  public boolean equals(final Object other) {
    if (!(other instanceof Patient)) {
      return false;
    }
    Patient castOther = (Patient) other;
    return Objects.equal(ssid, castOther.ssid)
        && Objects.equal(name, castOther.name)
        && Objects.equal(gender, castOther.gender)
        && Objects.equal(disease, castOther.disease)
        && Objects.equal(zipcode, castOther.zipcode)
        && Objects.equal(age, castOther.age);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(ssid, name, gender, disease, zipcode, age);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("ssid", ssid).add("name", name)
        .add("gender", gender).add("disease", disease).add("zipcode", zipcode)
        .add("age", age).toString();
  }

}
