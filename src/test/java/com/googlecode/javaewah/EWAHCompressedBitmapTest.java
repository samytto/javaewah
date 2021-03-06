package com.googlecode.javaewah;


/*
 * Copyright 2009-2012, Daniel Lemire, Cliff Moon, David McIntosh and Google Inc.
 * Licensed under APL 2.0.
 */

import org.junit.Test;

import java.util.*;
import java.io.*;




import junit.framework.Assert;

/**
 * This class is used for unit testing.
 */
public class EWAHCompressedBitmapTest {
  
  /**
   * Created: 2/4/11 6:03 PM By: Arnon Moscona.
   */
  @Test
  public void EwahIteratorProblem() {
    System.out.println("testing ArnonMoscona");
    EWAHCompressedBitmap bitmap = new EWAHCompressedBitmap();
    for (int i = 9434560; i <= 9435159; i++) {
      bitmap.set(i);
    }
    IntIterator iterator = bitmap.intIterator();
    List<Integer> v = bitmap.getPositions();
    int[] array = bitmap.toArray();
    for (int k = 0; k < v.size(); ++k) {
      Assert.assertTrue(array[k]== v.get(k).intValue());
      Assert.assertTrue(iterator.hasNext());
      final int ival = iterator.next();
      final int vval = v.get(k).intValue();
      Assert.assertTrue(ival == vval);
    }
    Assert.assertTrue(!iterator.hasNext());
    //
    for (int k = 2; k <= 1024; k *= 2) {
      int[] bitsToSet = createSortedIntArrayOfBitsToSet(k,434455 + 5*k);
      EWAHCompressedBitmap ewah = new EWAHCompressedBitmap();
      for (int i : bitsToSet) {
        ewah.set(i);
      }
      equal(ewah.iterator(), bitsToSet);
    }
  }
  
  /**
   * Test submitted by Gregory Ssi-Yan-Kai
   */
  @Test
  public void SsiYanKaiTest() {
    System.out.println("testing SsiYanKaiTest");
    EWAHCompressedBitmap a = EWAHCompressedBitmap.bitmapOf(39935, 39936, 39937, 39938, 39939, 39940, 39941, 39942, 39943, 39944, 39945, 39946, 39947, 39948, 39949, 39950, 39951, 39952, 39953, 39954, 39955, 39956, 39957, 39958, 39959, 39960, 39961, 39962, 39963, 39964, 39965, 39966, 39967, 39968, 39969, 39970, 39971, 39972, 39973, 39974, 39975, 39976, 39977, 39978, 39979, 39980, 39981, 39982, 39983, 39984, 39985, 39986, 39987, 39988, 39989, 39990, 39991, 39992, 39993, 39994, 39995, 39996, 39997, 39998, 39999, 40000, 40001, 40002, 40003, 40004, 40005, 40006, 40007, 40008, 40009, 40010, 40011, 40012, 40013, 40014, 40015, 40016, 40017, 40018, 40019, 40020, 40021, 40022, 40023, 40024, 40025, 40026, 40027, 40028, 40029, 40030, 40031, 40032, 40033, 40034, 40035, 40036, 40037, 40038, 40039, 40040, 40041, 40042, 40043, 40044, 40045, 40046, 40047, 40048, 40049, 40050, 40051, 40052, 40053, 40054, 40055, 40056, 40057, 40058, 40059, 40060, 40061, 40062, 40063, 40064, 40065, 40066, 40067, 40068, 40069, 40070, 40071, 40072, 40073, 40074, 40075, 40076, 40077, 40078, 40079, 40080, 40081, 40082, 40083, 40084, 40085, 40086, 40087, 40088, 40089, 40090, 40091, 40092, 40093, 40094, 40095, 40096, 40097, 40098, 40099, 40100);
    EWAHCompressedBitmap b = EWAHCompressedBitmap.bitmapOf(39935, 39936, 39937, 39938, 39939, 39940, 39941, 39942, 39943, 39944, 39945, 39946, 39947, 39948, 39949, 39950, 39951, 39952, 39953, 39954, 39955, 39956, 39957, 39958, 39959, 39960, 39961, 39962, 39963, 39964, 39965, 39966, 39967, 39968, 39969, 39970, 39971, 39972, 39973, 39974, 39975, 39976, 39977, 39978, 39979, 39980, 39981, 39982, 39983, 39984, 39985, 39986, 39987, 39988, 39989, 39990, 39991, 39992, 39993, 39994, 39995, 39996, 39997, 39998, 39999, 270000);
    LinkedHashSet<Integer> aPositions = new LinkedHashSet<Integer>(a.getPositions());    
    int intersection = 0;
    EWAHCompressedBitmap inter = new EWAHCompressedBitmap();
    LinkedHashSet<Integer> bPositions = new LinkedHashSet<Integer>(b.getPositions());
    for (Integer integer : bPositions) {
       if (aPositions.contains(integer)) {
         inter.set(integer.intValue());
          ++intersection;
       }
    }
    EWAHCompressedBitmap and2 = a.and(b);      
    if(!and2.equals(inter)) throw new RuntimeException("intersections don't match");
    if(intersection != and2.cardinality()) throw new RuntimeException("cardinalities don't match");
  }

  /**
   * Test inspired by William Habermaas.
   */
  @Test
  public void habermaasTest() {
    System.out.println("testing habermaasTest");
    BitSet bitsetaa = new BitSet();
    EWAHCompressedBitmap aa = new EWAHCompressedBitmap();
    int[] val = { 55400, 1000000, 1000128 };
    for (int k = 0; k < val.length; ++k) {
      aa.set(val[k]);
      bitsetaa.set(val[k]);
    }
    equal(aa, bitsetaa);
    BitSet bitsetab = new BitSet();
    EWAHCompressedBitmap ab = new EWAHCompressedBitmap();
    for (int i = 4096; i < (4096 + 5); i++) {
      ab.set(i);
      bitsetab.set(i);
    }
    ab.set(99000);
    bitsetab.set(99000);
    ab.set(1000130);
    bitsetab.set(1000130);
    equal(ab, bitsetab);
    EWAHCompressedBitmap bb = aa.or(ab);
    EWAHCompressedBitmap bbAnd = aa.and(ab);
    try {
      EWAHCompressedBitmap abnot = (EWAHCompressedBitmap)ab.clone();
      abnot.not();
      EWAHCompressedBitmap bbAnd2 = aa.andNot(abnot);
      assertEquals(bbAnd2,bbAnd);
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    BitSet bitsetbb = (BitSet) bitsetaa.clone();
    bitsetbb.or(bitsetab);
    BitSet bitsetbbAnd = (BitSet) bitsetaa.clone();
    bitsetbbAnd.and(bitsetab);
    equal(bbAnd, bitsetbbAnd);
    equal(bb, bitsetbb);
  }

  @Test
  public void testAndResultAppend()
  {
    System.out.println("testing AndResultAppend");
    EWAHCompressedBitmap bitmap1 = new EWAHCompressedBitmap();
    bitmap1.set(35);
    EWAHCompressedBitmap bitmap2 = new EWAHCompressedBitmap();
    bitmap2.set(35);
    bitmap2.set(130);

    EWAHCompressedBitmap resultBitmap = bitmap1.and(bitmap2);
    resultBitmap.set(131);

    bitmap1.set(131);
    assertEquals(bitmap1,resultBitmap);
  }

  /**
   * Test cardinality.
   */
  @Test
  public void testCardinality() {
    System.out.println("testing EWAH cardinality");
    EWAHCompressedBitmap bitmap = new EWAHCompressedBitmap();
    bitmap.set(Integer.MAX_VALUE - 64);
    // System.out.format("Total Items %d\n", bitmap.cardinality());
    Assert.assertTrue(bitmap.cardinality() == 1);
  }

  /**
   * Test clear function
   */
  @Test
  public void testClear() {
    System.out.println("testing Clear");
    EWAHCompressedBitmap bitmap = new EWAHCompressedBitmap();
    bitmap.set(5);
    bitmap.clear();
    bitmap.set(7);
    Assert.assertTrue(1 == bitmap.cardinality());
    Assert.assertTrue(1 == bitmap.getPositions().size());
    Assert.assertTrue(1 == bitmap.toArray().length);
    Assert.assertTrue(7 == bitmap.getPositions().get(0).intValue());
    Assert.assertTrue(7 == bitmap.toArray()[0]);
    bitmap.clear();
    bitmap.set( 5000 );
    Assert.assertTrue(1 == bitmap.cardinality());
    Assert.assertTrue(1 == bitmap.getPositions().size());
    Assert.assertTrue(1 == bitmap.toArray().length);
    Assert.assertTrue(5000 == bitmap.getPositions().get(0).intValue());
    bitmap.set(5001);
    bitmap.set(5005);
    bitmap.set(5100);
    bitmap.set(5500);
    bitmap.clear();
    bitmap.set(5);
    bitmap.set(7);
    bitmap.set(1000);
    bitmap.set(1001);
    Assert.assertTrue(4 == bitmap.cardinality());
    List<Integer> positions = bitmap.getPositions();
    Assert.assertTrue(4 == positions.size());
    Assert.assertTrue(5 == positions.get(0).intValue());
    Assert.assertTrue(7 == positions.get(1).intValue());
    Assert.assertTrue(1000 == positions.get(2).intValue());
    Assert.assertTrue(1001 == positions.get(3).intValue());
  }

  /**
   * Test ewah compressed bitmap.
   */
  @Test
  public void testEWAHCompressedBitmap() {
    System.out.println("testing EWAH");
    long zero = 0;
    long specialval = 1l | (1l << 4) | (1l << 63);
    long notzero = ~zero;
    EWAHCompressedBitmap myarray1 = new EWAHCompressedBitmap();
    myarray1.add(zero);
    myarray1.add(zero);
    myarray1.add(zero);
    myarray1.add(specialval);
    myarray1.add(specialval);
    myarray1.add(notzero);
    myarray1.add(zero);
    Assert.assertEquals(myarray1.getPositions().size(), 6 + 64);
    EWAHCompressedBitmap myarray2 = new EWAHCompressedBitmap();
    myarray2.add(zero);
    myarray2.add(specialval);
    myarray2.add(specialval);
    myarray2.add(notzero);
    myarray2.add(zero);
    myarray2.add(zero);
    myarray2.add(zero);
    Assert.assertEquals(myarray2.getPositions().size(), 6 + 64);
    List<Integer> data1 = myarray1.getPositions();
    List<Integer> data2 = myarray2.getPositions();
    Vector<Integer> logicalor = new Vector<Integer>();
    {
      HashSet<Integer> tmp = new HashSet<Integer>();
      tmp.addAll(data1);
      tmp.addAll(data2);
      logicalor.addAll(tmp);
    }
    Collections.sort(logicalor);
    Vector<Integer> logicaland = new Vector<Integer>();
    logicaland.addAll(data1);
    logicaland.retainAll(data2);
    Collections.sort(logicaland);
    EWAHCompressedBitmap arrayand = myarray1.and(myarray2);
    Assert.assertTrue(arrayand.getPositions().equals(logicaland));
    EWAHCompressedBitmap arrayor = myarray1.or(myarray2);
    Assert.assertTrue(arrayor.getPositions().equals(logicalor));
    EWAHCompressedBitmap arrayandbis = myarray2.and(myarray1);
    Assert.assertTrue(arrayandbis.getPositions().equals(logicaland));
    EWAHCompressedBitmap arrayorbis = myarray2.or(myarray1);
    Assert.assertTrue(arrayorbis.getPositions().equals(logicalor));
    EWAHCompressedBitmap x = new EWAHCompressedBitmap();
    for (Integer i : myarray1.getPositions()) {
      x.set(i.intValue());
    }
    Assert.assertTrue(x.getPositions().equals(myarray1.getPositions()));
    x = new EWAHCompressedBitmap();
    for (Integer i : myarray2.getPositions()) {
      x.set(i.intValue());
    }
    Assert.assertTrue(x.getPositions().equals(myarray2.getPositions()));
    x = new EWAHCompressedBitmap();
    for (Iterator<Integer> k = myarray1.iterator(); k.hasNext();) {
      x.set(extracted(k).intValue());
    }
    Assert.assertTrue(x.getPositions().equals(myarray1.getPositions()));
    x = new EWAHCompressedBitmap();
    for (Iterator<Integer> k = myarray2.iterator(); k.hasNext();) {
      x.set(extracted(k).intValue());
    }
    Assert.assertTrue(x.getPositions().equals(myarray2.getPositions()));
  }

  /**
   * Test externalization.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  public void testExternalization() throws IOException {
    System.out.println("testing EWAH externalization");
    EWAHCompressedBitmap ewcb = new EWAHCompressedBitmap();
    int[] val = { 5, 4400, 44600, 55400, 1000000 };
    for (int k = 0; k < val.length; ++k) {
      ewcb.set(val[k]);
    }
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream oo = new ObjectOutputStream(bos);
    ewcb.writeExternal(oo);
    oo.close();
    ewcb = null;
    ewcb = new EWAHCompressedBitmap();
    ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
    ewcb.readExternal(new ObjectInputStream(bis));
    List<Integer> result = ewcb.getPositions();
    Assert.assertTrue(val.length == result.size());
    for (int k = 0; k < val.length; ++k) {
      Assert.assertTrue(result.get(k).intValue() == val[k]);
    }
  }

  @Test
  public void testExtremeRange() {
    System.out.println("testing EWAH at its extreme range");
    int N = 1024;
    EWAHCompressedBitmap myarray1 = new EWAHCompressedBitmap();
    for (int i = 0; i < N; ++i) {
      myarray1.set(Integer.MAX_VALUE  - 64 - N + i);
      Assert.assertTrue(myarray1.cardinality() == i+1);
      int[] val = myarray1.toArray();
      Assert.assertTrue(val[0] == Integer.MAX_VALUE - 64 - N);
    }
  }

  /**
   * Test the intersects method
   */
  @Test
  public void testIntersectsMethod(){
      System.out.println("testing Intersets Bug");
      EWAHCompressedBitmap bitmap = new EWAHCompressedBitmap();
      bitmap.set(1);
      EWAHCompressedBitmap bitmap2 = new EWAHCompressedBitmap();
      bitmap2.set(1);
      bitmap2.set(11);
      bitmap2.set(111);
      bitmap2.set(1111111);
      bitmap2.set(11111111);
      Assert.assertTrue(bitmap.intersects(bitmap2));
      Assert.assertTrue(bitmap2.intersects(bitmap));

      EWAHCompressedBitmap bitmap3 = new EWAHCompressedBitmap();
      bitmap3.set(101);
      EWAHCompressedBitmap bitmap4 = new EWAHCompressedBitmap();
      for (int i = 0; i < 100; i++) {
          bitmap4.set(i);
      }
      Assert.assertFalse(bitmap3.intersects(bitmap4));
      Assert.assertFalse(bitmap4.intersects(bitmap3));

      EWAHCompressedBitmap bitmap5 = new EWAHCompressedBitmap();
      bitmap5.set(0);
      bitmap5.set(10);
      bitmap5.set(20);
      EWAHCompressedBitmap bitmap6 = new EWAHCompressedBitmap();
      bitmap6.set(1);
      bitmap6.set(11);
      bitmap6.set(21);
      bitmap6.set(1111111);
      bitmap6.set(11111111);
      Assert.assertFalse(bitmap5.intersects(bitmap6));
      Assert.assertFalse(bitmap6.intersects(bitmap5));

      bitmap5.set(21);
      Assert.assertTrue(bitmap5.intersects(bitmap6));
      Assert.assertTrue(bitmap6.intersects(bitmap5));

      EWAHCompressedBitmap bitmap7 = new EWAHCompressedBitmap();
      bitmap7.set(1);
      bitmap7.set(10);
      bitmap7.set(20);
      bitmap7.set(1111111);
      bitmap7.set(11111111);
      EWAHCompressedBitmap bitmap8 = new EWAHCompressedBitmap();
      for (int i = 0; i < 1000; i++) {
          if (i != 1 && i!=10 && i!=20){
              bitmap8.set(i);
          }
      }
      Assert.assertFalse(bitmap7.intersects(bitmap8));
      Assert.assertFalse(bitmap8.intersects(bitmap7));
  }
  
  /**
   * as per renaud.delbru, Feb 12, 2009 this might throw an error out of bound
   * exception.
   */
  @Test
  public void testLargeEWAHCompressedBitmap() {
    System.out.println("testing EWAH over a large array");
    EWAHCompressedBitmap myarray1 = new EWAHCompressedBitmap();
    int N = 11000000;
    for (int i = 0; i < N; ++i) {
      myarray1.set(i);
    }
    Assert.assertTrue(myarray1.sizeInBits() == N);
  }


  /**
   * Test massive and.
   */
  @Test
  public void testMassiveAnd() {
    System.out.println("testing massive logical and");
    EWAHCompressedBitmap[] ewah = new EWAHCompressedBitmap[1024];
    for (int k = 0; k < ewah.length; ++k)
      ewah[k] = new EWAHCompressedBitmap();
    for (int k = 0; k < 30000; ++k) {
      ewah[(k + 2 * k * k) % ewah.length].set(k);
    }
    EWAHCompressedBitmap answer = ewah[0];
    for (int k = 1; k < ewah.length; ++k)
      answer = answer.and(ewah[k]);
    // result should be empty
    if (answer.getPositions().size() != 0)
      System.out.println(answer.toDebugString());
    Assert.assertTrue(answer.getPositions().size() == 0);
    Assert.assertTrue(EWAHCompressedBitmap.and(ewah).getPositions().size() == 0);
  }

  /**
   * Test massive and not.
   */
  @Test
  public void testMassiveAndNot() {
    System.out.println("testing massive and not");
    final int N = 1024;
    EWAHCompressedBitmap[] ewah = new EWAHCompressedBitmap[N];
    for (int k = 0; k < ewah.length; ++k)
      ewah[k] = new EWAHCompressedBitmap();
    for (int k = 0; k < 30000; ++k) {
      ewah[(k + 2 * k * k) % ewah.length].set(k);
    }
    EWAHCompressedBitmap answer = ewah[0];
    EWAHCompressedBitmap answer2 = ewah[0];
    for (int k = 1; k < ewah.length; ++k) {
      answer = answer.andNot(ewah[k]);
      EWAHCompressedBitmap copy = null;
      try {
        copy = (EWAHCompressedBitmap) ewah[k].clone();
        copy.not();
        answer2.and(copy);
        assertEqualsPositions(answer, answer2);
      } catch (CloneNotSupportedException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Test massive or.
   */
  @Test
  public void testMassiveOr() {
    System.out.println("testing massive logical or (can take a couple of minutes)");
    final int N = 128;
    for (int howmany = 512; howmany <= 10000; howmany *= 2) {
      EWAHCompressedBitmap[] ewah = new EWAHCompressedBitmap[N];
      BitSet[] bset = new BitSet[N];
      for (int k = 0; k < ewah.length; ++k)
        ewah[k] = new EWAHCompressedBitmap();
      for (int k = 0; k < bset.length; ++k)
        bset[k] = new BitSet();
      for (int k = 0; k < N; ++k)
        assertEqualsPositions(bset[k], ewah[k]);
      for (int k = 0; k < howmany; ++k) {
        ewah[(k + 2 * k * k) % ewah.length].set(k);
        bset[(k + 2 * k * k) % ewah.length].set(k);
      }
      for (int k = 0; k < N; ++k)
        assertEqualsPositions(bset[k], ewah[k]);
      EWAHCompressedBitmap answer = ewah[0];
      BitSet bitsetanswer = bset[0];
      for (int k = 1; k < ewah.length; ++k) {
        EWAHCompressedBitmap tmp = answer.or(ewah[k]);
        bitsetanswer.or(bset[k]);
        answer = tmp;
        assertEqualsPositions(bitsetanswer, answer);
      }
      assertEqualsPositions(bitsetanswer, answer);
      assertEqualsPositions(bitsetanswer, EWAHCompressedBitmap.or(ewah));
      int k = 0;
      for (int j : answer) {
        if (k != j)
          System.out.println(answer.toDebugString());
        Assert.assertEquals(k, j);
        k += 1;
      }
    }
  }

  @Test
  public void testsetSizeInBits() {
	  System.out.println("testing setSizeInBits");
	  for(int k = 0; k < 4096; ++k) {
		  EWAHCompressedBitmap ewah = new EWAHCompressedBitmap();
		  ewah.setSizeInBits(k);
		  Assert.assertEquals(ewah.sizeinbits,k);
		  Assert.assertEquals(ewah.cardinality(),0);
		  EWAHCompressedBitmap ewah2 = new EWAHCompressedBitmap();
		  ewah2.setSizeInBits(k, false);
		  Assert.assertEquals(ewah2.sizeinbits,k);
		  Assert.assertEquals(ewah2.cardinality(),0);
		  EWAHCompressedBitmap ewah3 = new EWAHCompressedBitmap();
		  for(int i = 0; i < k ; ++i) {
			  ewah3.set(i);
		  }
		  Assert.assertEquals(ewah3.sizeinbits,k);
		  Assert.assertEquals(ewah3.cardinality(),k);
		  EWAHCompressedBitmap ewah4 = new EWAHCompressedBitmap();
		  ewah4.setSizeInBits(k, true);
		  Assert.assertEquals(ewah4.sizeinbits,k);
		  Assert.assertEquals(ewah4.cardinality(),k);
	  }
  }

  
  /**
   * Test massive xor.
   */
  @Test
  public void testMassiveXOR() {
    System.out.println("testing massive xor (can take a couple of minutes)");
    final int N = 16;
    EWAHCompressedBitmap[] ewah = new EWAHCompressedBitmap[N];
    BitSet[] bset = new BitSet[N];
    for (int k = 0; k < ewah.length; ++k)
      ewah[k] = new EWAHCompressedBitmap();
    for (int k = 0; k < bset.length; ++k)
      bset[k] = new BitSet();
    for (int k = 0; k < 30000; ++k) {
      ewah[(k + 2 * k * k) % ewah.length].set(k);
      bset[(k + 2 * k * k) % ewah.length].set(k);
    }
    EWAHCompressedBitmap answer = ewah[0];
    BitSet bitsetanswer = bset[0];
    for (int k = 1; k < ewah.length; ++k) {
      answer = answer.xor(ewah[k]);
      bitsetanswer.xor(bset[k]);
      assertEqualsPositions(bitsetanswer, answer);
    }
    int k = 0;
    for (int j : answer) {
      if (k != j)
        System.out.println(answer.toDebugString());
      Assert.assertEquals(k, j);
      k += 1;
    }
  }



  @Test
  public void testMultiAnd()
  {
    System.out.println("testing MultiAnd");
    // test bitmap3 has a literal word while bitmap1/2 have a run of 1
    EWAHCompressedBitmap bitmap1 = new EWAHCompressedBitmap();
    bitmap1.addStreamOfEmptyWords(true, 1000);
    EWAHCompressedBitmap bitmap2 = new EWAHCompressedBitmap();
    bitmap2.addStreamOfEmptyWords(true, 2000);
    EWAHCompressedBitmap bitmap3 = new EWAHCompressedBitmap();
    bitmap3.set(500);
    bitmap3.set(502);
    bitmap3.set(504);

    assertAndEquals(bitmap1,bitmap2,bitmap3);

    //equal
    bitmap1 = new EWAHCompressedBitmap();
    bitmap1.set(35);
    bitmap2 = new EWAHCompressedBitmap();
    bitmap2.set(35);
    bitmap3 = new EWAHCompressedBitmap();
    bitmap3.set(35);

    assertAndEquals(bitmap1,bitmap2,bitmap3);

    // same number of words for each
    bitmap3.set(63);
    assertAndEquals(bitmap1,bitmap2,bitmap3);

    // one word bigger
    bitmap3.set(64);
    assertAndEquals(bitmap1,bitmap2,bitmap3);

    // two words bigger
    bitmap3.set(130);
    assertAndEquals(bitmap1,bitmap2,bitmap3);

    // test that result can still be appended to
    EWAHCompressedBitmap resultBitmap = EWAHCompressedBitmap.and(bitmap1,bitmap2,bitmap3);
    resultBitmap.set(131);

    bitmap1.set(131);
    assertEquals(bitmap1,resultBitmap);

    final int N = 128;
    for (int howmany = 512; howmany <= 10000; howmany *= 2) {
      EWAHCompressedBitmap[] ewah = new EWAHCompressedBitmap[N];
      for (int k = 0; k < ewah.length; ++k)
        ewah[k] = new EWAHCompressedBitmap();
      for (int k = 0; k < howmany; ++k) {
        ewah[(k + 2 * k * k) % ewah.length].set(k);
      }
      for(int k = 1; k<=ewah.length;++k) {
        EWAHCompressedBitmap[] shortewah = new EWAHCompressedBitmap[k];
        for(int i = 0; i<k;++i) shortewah[i] = ewah[i];
        assertAndEquals(shortewah);
      }
    }
  }

  @Test
  public void testMultiOr()
  {
    System.out.println("testing MultiOr");
    // test bitmap3 has a literal word while bitmap1/2 have a run of 0
    EWAHCompressedBitmap bitmap1 = new EWAHCompressedBitmap();
    bitmap1.set(1000);
    EWAHCompressedBitmap bitmap2 = new EWAHCompressedBitmap();
    bitmap2.set(2000);
    EWAHCompressedBitmap bitmap3 = new EWAHCompressedBitmap();
    bitmap3.set(500);
    bitmap3.set(502);
    bitmap3.set(504);

    EWAHCompressedBitmap expected = bitmap1.or(bitmap2).or(bitmap3);

    assertEquals(expected, EWAHCompressedBitmap.or(bitmap1,bitmap2,bitmap3));

    final int N = 128;
    for (int howmany = 512; howmany <= 10000; howmany *= 2) {
      EWAHCompressedBitmap[] ewah = new EWAHCompressedBitmap[N];
      for (int k = 0; k < ewah.length; ++k)
        ewah[k] = new EWAHCompressedBitmap();
      for (int k = 0; k < howmany; ++k) {
        ewah[(k + 2 * k * k) % ewah.length].set(k);
      }
      for(int k = 1; k<=ewah.length;++k) {
        EWAHCompressedBitmap[] shortewah = new EWAHCompressedBitmap[k];
        for(int i = 0; i<k;++i) shortewah[i] = ewah[i];
        assertOrEquals(shortewah);
      }
    }

  
  }

  /**
   * Test not. (Based on an idea by Ciaran Jessup)
   */
  @Test
  public void testNot() {
    System.out.println("testing not");
    EWAHCompressedBitmap ewah = new EWAHCompressedBitmap();
    for(int i = 0; i<= 184; ++i) {
      ewah.set(i);
    }
    Assert.assertEquals(ewah.cardinality(),185);
    ewah.not();
    Assert.assertEquals(ewah.cardinality(),0);
  }

  @Test
  public void testOrCardinality() {
    System.out.println("testing Or Cardinality");
    for (int N = 0; N < 1024; ++N) {
      EWAHCompressedBitmap bitmap = new EWAHCompressedBitmap();
      for (int i = 0; i < N; i++) {
        bitmap.set(i);
      }
      bitmap.set(1025);
      bitmap.set(1026);
      Assert.assertEquals(N+2, bitmap.cardinality());
      EWAHCompressedBitmap orbitmap = bitmap.or(bitmap);
      assertEquals(orbitmap,bitmap);
      Assert.assertEquals(N+2, orbitmap.cardinality());

      Assert.assertEquals(N+2,
        bitmap.orCardinality(new EWAHCompressedBitmap()));
    }
  }

  /**
   * Test running length word.
   */
  @Test
  public void testRunningLengthWord() {
    System.out.println("testing RunningLengthWord");
    long x[] = new long[1];
    RunningLengthWord rlw = new RunningLengthWord(x, 0);
    Assert.assertEquals(0, rlw.getNumberOfLiteralWords());
    Assert.assertEquals(false, rlw.getRunningBit());
    Assert.assertEquals(0, rlw.getRunningLength());
    rlw.setRunningBit(true);
    Assert.assertEquals(0, rlw.getNumberOfLiteralWords());
    Assert.assertEquals(true, rlw.getRunningBit());
    Assert.assertEquals(0, rlw.getRunningLength());
    rlw.setRunningBit(false);
    Assert.assertEquals(0, rlw.getNumberOfLiteralWords());
    Assert.assertEquals(false, rlw.getRunningBit());
    Assert.assertEquals(0, rlw.getRunningLength());
    for (long rl = RunningLengthWord.largestliteralcount; rl >= 0; rl -= 1024) {
      rlw.setNumberOfLiteralWords(rl);
      Assert.assertEquals(rl, rlw.getNumberOfLiteralWords());
      Assert.assertEquals(false, rlw.getRunningBit());
      Assert.assertEquals(0, rlw.getRunningLength());
      rlw.setNumberOfLiteralWords(0);
      Assert.assertEquals(0, rlw.getNumberOfLiteralWords());
      Assert.assertEquals(false, rlw.getRunningBit());
      Assert.assertEquals(0, rlw.getRunningLength());
    }
    for (long rl = 0; rl <= RunningLengthWord.largestrunninglengthcount; rl += 1024) {
      rlw.setRunningLength(rl);
      Assert.assertEquals(0, rlw.getNumberOfLiteralWords());
      Assert.assertEquals(false, rlw.getRunningBit());
      Assert.assertEquals(rl, rlw.getRunningLength());
      rlw.setRunningLength(0);
      Assert.assertEquals(0, rlw.getNumberOfLiteralWords());
      Assert.assertEquals(false, rlw.getRunningBit());
      Assert.assertEquals(0, rlw.getRunningLength());
    }
    rlw.setRunningBit(true);
    for (long rl = 0; rl <= RunningLengthWord.largestrunninglengthcount; rl += 1024) {
      rlw.setRunningLength(rl);
      Assert.assertEquals(0, rlw.getNumberOfLiteralWords());
      Assert.assertEquals(true, rlw.getRunningBit());
      Assert.assertEquals(rl, rlw.getRunningLength());
      rlw.setRunningLength(0);
      Assert.assertEquals(0, rlw.getNumberOfLiteralWords());
      Assert.assertEquals(true, rlw.getRunningBit());
      Assert.assertEquals(0, rlw.getRunningLength());
    }
    for (long rl = 0; rl <= RunningLengthWord.largestliteralcount; rl += 128) {
      rlw.setNumberOfLiteralWords(rl);
      Assert.assertEquals(rl, rlw.getNumberOfLiteralWords());
      Assert.assertEquals(true, rlw.getRunningBit());
      Assert.assertEquals(0, rlw.getRunningLength());
      rlw.setNumberOfLiteralWords(0);
      Assert.assertEquals(0, rlw.getNumberOfLiteralWords());
      Assert.assertEquals(true, rlw.getRunningBit());
      Assert.assertEquals(0, rlw.getRunningLength());
    }
  }

  /**
   * Test sets and gets.
   */
  @Test
  public void testSetGet() {
    System.out.println("testing EWAH set/get");
    EWAHCompressedBitmap ewcb = new EWAHCompressedBitmap();
    int[] val = { 5, 4400, 44600, 55400, 1000000 };
    for (int k = 0; k < val.length; ++k) {
      ewcb.set(val[k]);
    }
    List<Integer> result = ewcb.getPositions();
    Assert.assertTrue(val.length == result.size());
    for (int k = 0; k < val.length; ++k) {
      Assert.assertEquals(result.get(k).intValue() , val[k]);
    }
  }
  
  @Test
  public void testHashCode() {
    System.out.println("testing hashCode");
    EWAHCompressedBitmap ewcb = EWAHCompressedBitmap.bitmapOf(50, 70).and(EWAHCompressedBitmap.bitmapOf(50, 1000));
    Assert.assertEquals(EWAHCompressedBitmap.bitmapOf(50), ewcb);
    Assert.assertEquals(EWAHCompressedBitmap.bitmapOf(50).hashCode(), ewcb.hashCode());
  }

  @Test
  public void testSetSizeInBits()
  {
    System.out.println("testing SetSizeInBits");
    testSetSizeInBits(130,131);
    testSetSizeInBits(63,64);
    testSetSizeInBits(64,65);
    testSetSizeInBits(64,128);
    testSetSizeInBits(35,131);
    testSetSizeInBits(130,400);
    testSetSizeInBits(130,191);
    testSetSizeInBits(130,192);
    EWAHCompressedBitmap bitmap = new EWAHCompressedBitmap();
    bitmap.set(31);
    bitmap.setSizeInBits(130,false);
    bitmap.set(131);
    BitSet jdkBitmap = new BitSet();
    jdkBitmap.set(31);
    jdkBitmap.set(131);
    assertEquals(jdkBitmap,bitmap);    
  }
  /**
   * Test with parameters.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  public void testWithParameters() throws IOException {
    System.out
      .println("These tests can run for several minutes. Please be patient.");
    for (int k = 2; k < 1 << 24; k *= 8)
      shouldSetBits(k);
    PolizziTest(64);
    PolizziTest(128);
    PolizziTest(256);
    PolizziTest(2048);
    System.out.println("Your code is probably ok.");
  }
     
  /**
   * Pseudo-non-deterministic test inspired by S.J.vanSchaik.
   * (Yes, non-deterministic tests are bad, but the test is actually deterministic.)
   */
  @Test
  public void vanSchaikTest() {
    System.out.println("testing vanSchaikTest (this takes some time)");
    final int totalNumBits = 32768;
    final double odds = 0.9;
    Random rand = new Random(323232323);
    for (int t = 0; t < 100; t++) {
      int numBitsSet = 0;
      EWAHCompressedBitmap cBitMap = new EWAHCompressedBitmap();
      for (int i = 0; i < totalNumBits; i++) {
        if (rand.nextDouble() < odds) {
          cBitMap.set(i);
          numBitsSet++;
        }
      }
      Assert.assertEquals(cBitMap.cardinality(),numBitsSet);
    }
    
  }

  /**
   * Function used in a test inspired by Federico Fissore.
   *
   * @param size the number of set bits
   * @param seed the random seed
   * @return the pseudo-random array int[]
   */
  public static int[] createSortedIntArrayOfBitsToSet(int size, int seed) {
    Random random = new Random(seed);
    // build raw int array
    int[] bits = new int[size];
    for (int i = 0; i < bits.length; i++) {
      bits[i] = random.nextInt(TEST_BS_SIZE);
    }
    // might generate duplicates
    Arrays.sort(bits);
    // first count how many distinct values
    int counter = 0;
    int oldx = -1;
    for (int x : bits) {
      if (x != oldx)
        ++counter;
      oldx = x;
    }
    // then construct new array
    int[] answer = new int[counter];
    counter = 0;
    oldx = -1;
    for (int x : bits) {
      if (x != oldx) {
        answer[counter] = x;
        ++counter;
      }
      oldx = x;
    }
    return answer;
  }
  /**
   * Test inspired by Bilal Tayara
   */
  @Test
  public void TayaraTest() {
    System.out.println("Tayara test");
    for(int offset = 64; offset<(1<<30);offset*=2){
      EWAHCompressedBitmap a = new EWAHCompressedBitmap();
      EWAHCompressedBitmap b = new EWAHCompressedBitmap();
      for(int k = 0; k< 64; ++k)  {
        a.set(offset+k);
        b.set(offset+k);
      }
      if(!a.and(b).equals(a)) throw new RuntimeException("bug");
      if(!a.or(b).equals(a)) throw new RuntimeException("bug");
    }
  }

  /**
   * a non-deterministic test proposed by Marc Polizzi.
   *
   * @param maxlength the maximum uncompressed size of the bitmap
   */
  public static void PolizziTest(int maxlength) {
    System.out.println("Polizzi test with max length = " + maxlength);
    for (int k = 0; k < 10000; ++k) {
      final Random rnd = new Random();
      final EWAHCompressedBitmap ewahBitmap1 = new EWAHCompressedBitmap();
      final BitSet jdkBitmap1 = new BitSet();
      final EWAHCompressedBitmap ewahBitmap2 = new EWAHCompressedBitmap();
      final BitSet jdkBitmap2 = new BitSet();
      final EWAHCompressedBitmap ewahBitmap3 = new EWAHCompressedBitmap();
      final BitSet jdkBitmap3 = new BitSet();
      final int len = rnd.nextInt(maxlength);
      for (int pos = 0; pos < len; pos++) { // random *** number of bits set ***
        if (rnd.nextInt(7) == 0) { // random *** increasing *** values
          ewahBitmap1.set(pos);
          jdkBitmap1.set(pos);
        }
        if (rnd.nextInt(11) == 0) { // random *** increasing *** values
          ewahBitmap2.set(pos);
          jdkBitmap2.set(pos);
        }
        if (rnd.nextInt(7) == 0) { // random *** increasing *** values
          ewahBitmap3.set(pos);
          jdkBitmap3.set(pos);
        }
      }
      assertEquals(jdkBitmap1, ewahBitmap1);
      assertEquals(jdkBitmap2, ewahBitmap2);
      assertEquals(jdkBitmap3, ewahBitmap3);
      // XOR
      {
        final EWAHCompressedBitmap xorEwahBitmap = ewahBitmap1.xor(ewahBitmap2);
        final BitSet xorJdkBitmap = (BitSet) jdkBitmap1.clone();
        xorJdkBitmap.xor(jdkBitmap2);
        assertEquals(xorJdkBitmap, xorEwahBitmap);
      }
      // AND
      {
        final EWAHCompressedBitmap andEwahBitmap = ewahBitmap1.and(ewahBitmap2);
        final BitSet andJdkBitmap = (BitSet) jdkBitmap1.clone();
        andJdkBitmap.and(jdkBitmap2);
        assertEquals(andJdkBitmap, andEwahBitmap);
      }
      // AND
      {
        final EWAHCompressedBitmap andEwahBitmap = ewahBitmap2.and(ewahBitmap1);
        final BitSet andJdkBitmap = (BitSet) jdkBitmap1.clone();
        andJdkBitmap.and(jdkBitmap2);
        assertEquals(andJdkBitmap, andEwahBitmap);
        assertEquals(andJdkBitmap, EWAHCompressedBitmap.and(ewahBitmap1, ewahBitmap2));
      }
      // MULTI AND
      {
        final BitSet andJdkBitmap = (BitSet) jdkBitmap1.clone();
        andJdkBitmap.and(jdkBitmap2);
        andJdkBitmap.and(jdkBitmap3);
        assertEquals(andJdkBitmap, EWAHCompressedBitmap.and(ewahBitmap1, ewahBitmap2, ewahBitmap3));
        assertEquals(andJdkBitmap, EWAHCompressedBitmap.and(ewahBitmap3, ewahBitmap2, ewahBitmap1));
        Assert.assertEquals(andJdkBitmap.cardinality(), EWAHCompressedBitmap.andCardinality(ewahBitmap1, ewahBitmap2, ewahBitmap3));
      }
      // AND NOT
      {
        final EWAHCompressedBitmap andNotEwahBitmap = ewahBitmap1
          .andNot(ewahBitmap2);
        final BitSet andNotJdkBitmap = (BitSet) jdkBitmap1.clone();
        andNotJdkBitmap.andNot(jdkBitmap2);
        assertEquals(andNotJdkBitmap, andNotEwahBitmap);
      }
      // AND NOT
      {
        final EWAHCompressedBitmap andNotEwahBitmap = ewahBitmap2
          .andNot(ewahBitmap1);
        final BitSet andNotJdkBitmap = (BitSet) jdkBitmap2.clone();
        andNotJdkBitmap.andNot(jdkBitmap1);
        assertEquals(andNotJdkBitmap, andNotEwahBitmap);
      }
      // OR
      {
        final EWAHCompressedBitmap orEwahBitmap = ewahBitmap1.or(ewahBitmap2);
        final BitSet orJdkBitmap = (BitSet) jdkBitmap1.clone();
        orJdkBitmap.or(jdkBitmap2);
        assertEquals(orJdkBitmap, orEwahBitmap);
        assertEquals(orJdkBitmap, EWAHCompressedBitmap.or(ewahBitmap1, ewahBitmap2));
        Assert.assertEquals(orEwahBitmap.cardinality(), ewahBitmap1.orCardinality(ewahBitmap2));
      }
      // OR
      {
        final EWAHCompressedBitmap orEwahBitmap = ewahBitmap2.or(ewahBitmap1);
        final BitSet orJdkBitmap = (BitSet) jdkBitmap1.clone();
        orJdkBitmap.or(jdkBitmap2);
        assertEquals(orJdkBitmap, orEwahBitmap);
      }
      // MULTI OR
      {
        final BitSet orJdkBitmap = (BitSet) jdkBitmap1.clone();
        orJdkBitmap.or(jdkBitmap2);
        orJdkBitmap.or(jdkBitmap3);
        assertEquals(orJdkBitmap, EWAHCompressedBitmap.or(ewahBitmap1, ewahBitmap2, ewahBitmap3));
        assertEquals(orJdkBitmap, EWAHCompressedBitmap.or(ewahBitmap3, ewahBitmap2, ewahBitmap1));
        Assert.assertEquals(orJdkBitmap.cardinality(), EWAHCompressedBitmap.orCardinality(ewahBitmap1, ewahBitmap2, ewahBitmap3));
      }
    }
  }

  /**
   * Pseudo-non-deterministic test inspired by Federico Fissore.
   *
   * @param length the number of set bits in a bitmap
   */
  public static void shouldSetBits(int length) {
    System.out.println("testing shouldSetBits " + length);
    int[] bitsToSet = createSortedIntArrayOfBitsToSet(length,434222);
    EWAHCompressedBitmap ewah = new EWAHCompressedBitmap();
    System.out.println(" ... setting " + bitsToSet.length + " values");
    for (int i : bitsToSet) {
      ewah.set(i);
    }
    System.out.println(" ... verifying " + bitsToSet.length + " values");
    equal(ewah.iterator(), bitsToSet);
    System.out.println(" ... checking cardinality");
    Assert.assertEquals(bitsToSet.length, ewah.cardinality());
  }


  private static void assertAndEquals(EWAHCompressedBitmap...bitmaps)
  {
    EWAHCompressedBitmap expected = bitmaps[0];
    for(int i = 1; i < bitmaps.length; i++) {
      expected = expected.and(bitmaps[i]);
    }
    assertEquals(expected, EWAHCompressedBitmap.and(bitmaps));
  }
  
  private static void assertEquals(EWAHCompressedBitmap expected, EWAHCompressedBitmap actual) {
    Assert.assertEquals(expected.sizeInBits(), actual.sizeInBits());
    assertEqualsPositions(expected, actual);
  }

  private static void assertOrEquals(EWAHCompressedBitmap...bitmaps)
  {
    EWAHCompressedBitmap expected = bitmaps[0];
    for(int i = 1; i < bitmaps.length; i++) {
      expected = expected.or(bitmaps[i]);
    }
    assertEquals(expected, EWAHCompressedBitmap.or(bitmaps));
  }

  /**
   * Extracted.
   *
   * @param bits the bits
   * @return the integer
   */
  private static Integer extracted(final Iterator<Integer> bits) {
    return bits.next();
  }


  private static void testSetSizeInBits(int size, int nextBit) {
    EWAHCompressedBitmap bitmap = new EWAHCompressedBitmap();
    bitmap.setSizeInBits(size,false);
    bitmap.set(nextBit);
    BitSet jdkBitmap = new BitSet();
    jdkBitmap.set(nextBit);
    assertEquals(jdkBitmap,bitmap);
  }

  /**
   * Assess equality between an uncompressed bitmap and a compressed one,
   * part of a test contributed by Marc Polizzi
   *
   * @param jdkBitmap the uncompressed bitmap
   * @param ewahBitmap the compressed bitmap
   */
  static void assertCardinality(BitSet jdkBitmap,
    EWAHCompressedBitmap ewahBitmap) {
    final int c1 = jdkBitmap.cardinality();
    final int c2 = ewahBitmap.cardinality();
    Assert.assertEquals(c1, c2);
  }
  
  /**
   * Assess equality between an uncompressed bitmap and a compressed one,
   *  part of a test contributed by Marc Polizzi.
   *
   * @param jdkBitmap the uncompressed bitmap
   * @param ewahBitmap the compressed bitmap
   */
  static void assertEquals(BitSet jdkBitmap, EWAHCompressedBitmap ewahBitmap) {
    assertEqualsIterator(jdkBitmap, ewahBitmap);
    assertEqualsPositions(jdkBitmap, ewahBitmap);
    assertCardinality(jdkBitmap, ewahBitmap);
  }

  static void assertEquals(int[] v, List<Integer> p) {
    assertEquals(p,v);
  }
  static void assertEquals(List<Integer> p, int[] v) {
    if(v.length!= p.size())
      throw new RuntimeException("Different lengths   "+v.length+ " "+p.size());
    for(int k = 0; k<v.length;++k)
      if(v[k]!= p.get(k).intValue())
        throw new RuntimeException("expected equal at "+k+" "+v[k]+" "+p.get(k));
  }

  // 
  /**
   * Assess equality between an uncompressed bitmap and a compressed one,
   * part of a test contributed by Marc Polizzi
   *
   * @param jdkBitmap the jdk bitmap
   * @param ewahBitmap the ewah bitmap
   */
  static void assertEqualsIterator(BitSet jdkBitmap,
    EWAHCompressedBitmap ewahBitmap) {
    final Vector<Integer> positions = new Vector<Integer>();
    final Iterator<Integer> bits = ewahBitmap.iterator();
    while (bits.hasNext()) {
      final int bit = extracted(bits).intValue();
      Assert.assertTrue(jdkBitmap.get(bit));
      positions.add(new Integer(bit));
    }
    for (int pos = jdkBitmap.nextSetBit(0); pos >= 0; pos = jdkBitmap
      .nextSetBit(pos + 1)) {
      if (!positions.contains(new Integer(pos))) {
        throw new RuntimeException("iterator: bitset got different bits");
      }
    }
  }

  // part of a test contributed by Marc Polizzi
  /**
   * Assert equals positions.
   *
   * @param jdkBitmap the jdk bitmap
   * @param ewahBitmap the ewah bitmap
   */
  static void assertEqualsPositions(BitSet jdkBitmap,
    EWAHCompressedBitmap ewahBitmap) {
    final List<Integer> positions = ewahBitmap.getPositions();
    for (int position : positions) {
      if (!jdkBitmap.get(position)) {
        throw new RuntimeException("positions: bitset got different bits");
      }
    }
    for (int pos = jdkBitmap.nextSetBit(0); pos >= 0; pos = jdkBitmap
      .nextSetBit(pos + 1)) {
      if (!positions.contains(new Integer(pos))) {
        throw new RuntimeException("positions: bitset got different bits");
      }
    }
    // we check again
    final int[] fastpositions = ewahBitmap.toArray();
    for (int position : fastpositions) {
      if (!jdkBitmap.get(position)) {
        throw new RuntimeException("positions: bitset got different bits with toArray");
      }
    }
    for (int pos = jdkBitmap.nextSetBit(0); pos >= 0; pos = jdkBitmap
      .nextSetBit(pos + 1)) {
      int index = Arrays.binarySearch(fastpositions,pos);
      if(index < 0)
        throw new RuntimeException("positions: bitset got different bits with toArray");
      if(fastpositions[index]!= pos)
        throw new RuntimeException("positions: bitset got different bits with toArray");
    }
  }

  /**
   * Assert equals positions.
   *
   * @param ewahBitmap1 the ewah bitmap1
   * @param ewahBitmap2 the ewah bitmap2
   */
  static void assertEqualsPositions(EWAHCompressedBitmap ewahBitmap1,
    EWAHCompressedBitmap ewahBitmap2) {
    final List<Integer> positions1 = ewahBitmap1.getPositions();
    final List<Integer> positions2 = ewahBitmap2.getPositions();
    if (!positions1.equals(positions2))
      throw new RuntimeException("positions: alternative got different bits (two bitmaps)");
    //
    final int[] fastpositions1 = ewahBitmap1.toArray();
    assertEquals(fastpositions1, positions1);
    final int[] fastpositions2 = ewahBitmap2.toArray();
    assertEquals(fastpositions2, positions2);
    if (!Arrays.equals(fastpositions1, fastpositions2))
      throw new RuntimeException("positions: alternative got different bits with toArray but not with getPositions (two bitmaps)");
  }

  /**
   * Convenience function to assess equality between a compressed bitset 
   * and an uncompressed bitset
   *
   * @param x the compressed bitset/bitmap
   * @param y the uncompressed bitset/bitmap
   */
  static void equal(EWAHCompressedBitmap x, BitSet y) {
    Assert.assertEquals (x.cardinality() , y.cardinality());
    for (int i : x.getPositions())
      Assert.assertTrue(y.get(i));
  }

  /**
   * Convenience function to assess equality between an array and an iterator over
   * Integers
   *
   * @param i the iterator
   * @param array the array
   */
  static void equal(Iterator<Integer> i, int[] array) {
    int cursor = 0;
    while (i.hasNext()) {
      int x = extracted(i).intValue();
      int y = array[cursor++];
      Assert.assertEquals(x,y);
    }
  }
  
  /** The Constant MEGA: a large integer. */
  private static final int MEGA = 8 * 1024 * 1024;

  /** The Constant TEST_BS_SIZE: used to represent the size of a large bitmap. */
  private static final int TEST_BS_SIZE = 8 * MEGA;
}
