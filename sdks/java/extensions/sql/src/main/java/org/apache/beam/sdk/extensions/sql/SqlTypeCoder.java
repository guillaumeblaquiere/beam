/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.beam.sdk.extensions.sql;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.beam.sdk.coders.BigDecimalCoder;
import org.apache.beam.sdk.coders.BigEndianIntegerCoder;
import org.apache.beam.sdk.coders.BigEndianLongCoder;
import org.apache.beam.sdk.coders.ByteCoder;
import org.apache.beam.sdk.coders.Coder;
import org.apache.beam.sdk.coders.CoderException;
import org.apache.beam.sdk.coders.CustomCoder;
import org.apache.beam.sdk.coders.StringUtf8Coder;

/**
 * Base class for coders for supported SQL types.
 */
public abstract class SqlTypeCoder extends CustomCoder<Object> {

  @Override
  public void encode(Object value, OutputStream outStream) throws CoderException, IOException {
    delegateCoder().encode(value, outStream);
  }

  @Override
  public Object decode(InputStream inStream) throws CoderException, IOException {
    return delegateCoder().decode(inStream);
  }

  @Override
  public void verifyDeterministic() throws NonDeterministicException {
    delegateCoder().verifyDeterministic();
  }

  protected abstract Coder delegateCoder();

  @Override
  public boolean equals(Object other) {
    return other != null && this.getClass().equals(other.getClass());

  }

  @Override
  public int hashCode() {
    return this.getClass().hashCode();
  }

  static class SqlTinyIntCoder extends SqlTypeCoder {
    @Override
    protected Coder delegateCoder() {
      return ByteCoder.of();
    }
  }

  static class SqlSmallIntCoder extends SqlTypeCoder {
    @Override
    protected Coder delegateCoder() {
      return BeamSqlRecordHelper.ShortCoder.of();
    }
  }

  static class SqlIntegerCoder extends SqlTypeCoder {
    @Override
    protected Coder delegateCoder() {
      return BigEndianIntegerCoder.of();
    }
  }

  static class SqlBigIntCoder extends SqlTypeCoder {
    @Override
    protected Coder delegateCoder() {
      return BigEndianLongCoder.of();
    }
  }

  static class SqlFloatCoder extends SqlTypeCoder {
    @Override
    protected Coder delegateCoder() {
      return BeamSqlRecordHelper.FloatCoder.of();
    }
  }

  static class SqlDoubleCoder extends SqlTypeCoder {
    @Override
    protected Coder delegateCoder() {
      return BeamSqlRecordHelper.DoubleCoder.of();
    }
  }

  static class SqlDecimalCoder extends SqlTypeCoder {
    @Override
    protected Coder delegateCoder() {
      return BigDecimalCoder.of();
    }
  }

  static class SqlBooleanCoder extends SqlTypeCoder {
    @Override
    protected Coder delegateCoder() {
      return BeamSqlRecordHelper.BooleanCoder.of();
    }
  }

  static class SqlCharCoder extends SqlTypeCoder {
    @Override
    protected Coder delegateCoder() {
      return StringUtf8Coder.of();
    }
  }

  static class SqlVarCharCoder extends SqlTypeCoder {
    @Override
    protected Coder delegateCoder() {
      return StringUtf8Coder.of();
    }
  }

  static class SqlTimeCoder extends SqlTypeCoder {
    @Override
    protected Coder delegateCoder() {
      return BeamSqlRecordHelper.TimeCoder.of();
    }
  }

  static class SqlDateCoder extends SqlTypeCoder {
    @Override
    protected Coder delegateCoder() {
      return BeamSqlRecordHelper.DateCoder.of();
    }
  }

  static class SqlTimestampCoder extends SqlTypeCoder {
    @Override
    protected Coder delegateCoder() {
      return BeamSqlRecordHelper.DateCoder.of();
    }
  }
}
