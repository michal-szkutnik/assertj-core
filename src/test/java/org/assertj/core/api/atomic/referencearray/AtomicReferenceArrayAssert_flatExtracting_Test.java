/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2017 the original author or authors.
 */
package org.assertj.core.api.atomic.referencearray;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;

import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceArray;

import org.assertj.core.api.iterable.Extractor;
import org.assertj.core.test.CartoonCharacter;
import org.assertj.core.test.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class AtomicReferenceArrayAssert_flatExtracting_Test {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private CartoonCharacter bart;
  private CartoonCharacter lisa;
  private CartoonCharacter maggie;
  private CartoonCharacter homer;
  private CartoonCharacter pebbles;
  private CartoonCharacter fred;

  private final Extractor<CartoonCharacter, List<CartoonCharacter>> children = new Extractor<CartoonCharacter, List<CartoonCharacter>>() {
    @Override
    public List<CartoonCharacter> extract(CartoonCharacter input) {
      return input.getChildren();
    }
  };

  @Before
  public void setUp() {
    bart = new CartoonCharacter("Bart Simpson");
    lisa = new CartoonCharacter("Lisa Simpson");
    maggie = new CartoonCharacter("Maggie Simpson");

    homer = new CartoonCharacter("Homer Simpson");
    homer.addChildren(bart, lisa, maggie);

    pebbles = new CartoonCharacter("Pebbles Flintstone");
    fred = new CartoonCharacter("Fred Flintstone");
    fred.addChildren(pebbles);
  }

  @Test
  public void should_allow_assertions_on_joined_lists_when_extracting_children() {
    AtomicReferenceArray<CartoonCharacter> cartoonCharacters = new AtomicReferenceArray<>(array(homer, fred));
    assertThat(cartoonCharacters).flatExtracting(children).containsOnly(bart, lisa, maggie, pebbles);
  }

  @Test
  public void should_allow_assertions_on_empty_result_lists() {
    AtomicReferenceArray<CartoonCharacter> childCharacters = new AtomicReferenceArray<>(array(bart, lisa, maggie));
    assertThat(childCharacters).flatExtracting(children).isEmpty();
  }

  @Test
  public void should_throw_null_pointer_exception_when_extracting_from_null() {
    thrown.expectNullPointerException();
    assertThat(new AtomicReferenceArray<>(array(homer, null))).flatExtracting(children);
  }

}
