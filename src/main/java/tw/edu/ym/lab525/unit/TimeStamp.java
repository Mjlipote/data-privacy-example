/**
 *
 * @author Wei-Ming Wu
 *
 *
 * Copyright 2013 Wei-Ming Wu
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
package tw.edu.ym.lab525.unit;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * TimeStamp provides friendly methods to generate many kinds of timestamps.
 * 
 */
public final class TimeStamp {

  private static final SimpleDateFormat dateFormat;
  private static final SimpleDateFormat dateTimeFormat;
  private static final SimpleDateFormat simpleDateFormat;
  private static final SimpleDateFormat simpleDateTimeFormat;
  private static final SimpleDateFormat simpleDateTimeMillisecondFormat;

  static {
    dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss");
    simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    simpleDateTimeFormat = new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss");
    simpleDateTimeMillisecondFormat = new SimpleDateFormat("yyyyMMddHHmmssFFF");
  }

  private TimeStamp() {}

  /**
   * Creates a date(yyyy/MM/dd) timestamp.
   * 
   * @return a date timestamp
   */
  public static String date() {
    return dateFormat.format(new Date());
  }

  /**
   * Creates a datetime(yyyy/MM/dd HH:mm:ss) timestamp.
   * 
   * @return a datetime timestamp
   */
  public static String dateTime() {
    return dateTimeFormat.format(new Date());
  }

  /**
   * Creates a simple date(yyyyMMdd) timestamp.
   * 
   * @return a simple date timestamp
   */
  public static String simpleDate() {
    return simpleDateFormat.format(new Date());
  }

  /**
   * Creates a simple datetime(yyyyMMddHHmmss) timestamp.
   * 
   * @return a simple datetime timestamp
   */
  public static String simpleDateTime() {
    return simpleDateTimeFormat.format(new Date());
  }

  public static String simpleDateTimeMillisecond() {
    return simpleDateTimeMillisecondFormat.format(new Date());
  }

}