/**
 * 
 */
package com.mindtree.devtools.b2b.test;

import gen.b2b.BeanAToBeanCMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.mindtree.devtools.b2b.sample.BeanA;
import com.mindtree.devtools.b2b.sample.BeanB;
import com.mindtree.devtools.b2b.sample.BeanC;
import com.mindtree.devtools.b2b.sample.ComplexObject;
import com.mindtree.devtools.b2b.sample.SourceBean;
import com.mindtree.devtools.utils.DevToolsUtils;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.utils.CopyUtils;


public class TestDeepCollectionCopier{

	/**
	 * @param args
	 */
	public static void main( String[] args ){
		aToC();
		cToA();
	}

	private static void separateOutputs(){
		System.out.println();
		System.out.println( "*********************************" );
		System.out.println();
	}

	/**
	 * @return
	 */
	private static BeanA[] populateArrayOfBeanA(){
		BeanA[] beanAArray = new BeanA[ 10 ];
		for( int i = 0; i < 10; i++ ){
			beanAArray[ i ] = populateBeanA();
		}
		return beanAArray;
	}

	private static BeanA populateBeanA(){
		BeanA beanA = new BeanA();
		
		beanA.setStr( "Hi" );

		List<ComplexObject> complexObjectList = populateComplexObjectList();

		Set<ComplexObject> complexObjectSet = populateComplexObjectSet();

		ComplexObject[] complexObjectArray = populateComplexObjectArray();

		beanA.setComplexObjectList( complexObjectList );

		beanA.setComplexObjectSet( complexObjectSet );

		beanA.setComplexObjectArray( complexObjectArray );

		return beanA;
	}
	
	private static void aToC(){
		//List of typeA to List of TypeB
		List<BeanC> beanCList = (List<BeanC>) CopyUtils.copy( populateListOfBeanA(), new ArrayList<BeanC>(), BeanAToBeanCMapper.class );
		printBeanCList( beanCList );
		separateOutputs();

		//List of typeA to Set of TypeB
		Set<BeanC> beanCSet = (Set<BeanC>) CopyUtils.copy( populateListOfBeanA(), new HashSet<BeanC>(), BeanAToBeanCMapper.class );
		printBeanCSet( beanCSet );
		separateOutputs();

		//List of typeA to Array of TypeB
		BeanC[] beanCArray = (BeanC[]) CopyUtils.copy( populateListOfBeanA(), new BeanC[ populateArrayOfBeanA().length ], BeanAToBeanCMapper.class );
		printBeanCArray( beanCArray );
		separateOutputs();

		//Set of typeA to List of TypeB
		List<BeanC> beanCList2 = (List<BeanC>) CopyUtils.copy( populateBeanASet(), new ArrayList<BeanC>(), BeanAToBeanCMapper.class );
		printBeanCList( beanCList2 );
		separateOutputs();

		//Set of typeA to array of TypeB
		BeanC[] beanCArray2 = (BeanC[]) CopyUtils.copy( populateBeanASet(), new BeanC[ populateArrayOfBeanA().length ], BeanAToBeanCMapper.class );
		printBeanCArray( beanCArray2 );
		separateOutputs();

		//Set of typeA to set of TypeB
		Set<BeanC> beanCSet2 = (Set<BeanC>) CopyUtils.copy( populateBeanASet(), new HashSet<BeanC>(), BeanAToBeanCMapper.class );
		printBeanCSet( beanCSet2 );
		separateOutputs();

		//Array of typeA to array of TypeB
		BeanC[] beanCArray3 = (BeanC[]) CopyUtils.copy( populateArrayOfBeanA(), new BeanC[ populateArrayOfBeanA().length ], BeanAToBeanCMapper.class );
		printBeanCArray( beanCArray3 );
		separateOutputs();

		//Array of typeA to List of TypeB
		List<BeanC> beanCList3 = (List<BeanC>) CopyUtils.copy( populateArrayOfBeanA(), new ArrayList<BeanC>(), BeanAToBeanCMapper.class );
		printBeanCList( beanCList3 );
		separateOutputs();

		//List of typeA to Set of TypeB
		Set<BeanC> beanCSet3 = (Set<BeanC>) CopyUtils.copy( populateArrayOfBeanA(), new HashSet<BeanC>(), BeanAToBeanCMapper.class );
		printBeanCSet( beanCSet3 );
	}

	/**
	 * @param <T>
	 * @return
	 */
	private static Set<BeanA> populateBeanASet(){
		Set<BeanA> beanASet = DevToolsUtils.asSet( populateListOfBeanA() );
		return beanASet;
	}

	/**
	 * @return
	 */
	private static ComplexObject[] populateComplexObjectArray(){
		//Complex object Set
		ComplexObject[] complexObjectArray = new ComplexObject[ 2 ];

		//1
		ComplexObject complexObject1 = new ComplexObject();

		complexObject1.setString( com.Constant.CONST_COMPLEXOBJECT_STRING1 );

		List<String> stringList1 = new ArrayList<String>();
		stringList1.add( com.Constant.CONST_COMPLEXOBJECT_LIST_STRING_STRING1 );
		stringList1.add( com.Constant.CONST_COMPLEXOBJECT_LIST_STRING_STRING2 );
		complexObject1.setStringList( stringList1 );

		List<BeanC> beanCList1 = new ArrayList<BeanC>();
		BeanC beanC1 = new BeanC();
		beanC1.setString( com.Constant.CONST_BEANC1_STRING );
		beanCList1.add( beanC1 );
		BeanC beanC2 = new BeanC();
		beanC2.setString( com.Constant.CONST_BEANC2_STRING );
		beanCList1.add( beanC2 );
		complexObject1.setBeanCList( beanCList1 );

		complexObjectArray[ 0 ] = complexObject1;

		//2
		ComplexObject complexObject2 = new ComplexObject();

		complexObject2.setString( com.Constant.CONST_COMPLEXOBJECT_STRING2 );
		List<String> stringList2 = new ArrayList<String>();
		stringList2.add( com.Constant.CONST_COMPLEXOBJECT_LIST_STRING_STRING3 );
		stringList2.add( com.Constant.CONST_COMPLEXOBJECT_LIST_STRING_STRING4 );
		complexObject2.setStringList( stringList2 );

		List<BeanC> beanCList2 = new ArrayList<BeanC>();
		BeanC beanC3 = new BeanC();
		beanC3.setString( com.Constant.CONST_BEANC3_STRING );
		beanCList2.add( beanC3 );
		BeanC beanC4 = new BeanC();
		beanC4.setString( com.Constant.CONST_BEANC4_STRING );
		beanCList2.add( beanC4 );
		complexObject2.setBeanCList( beanCList2 );

		complexObjectArray[ 1 ] = complexObject2;

		return complexObjectArray;
	}

	/**
	 * @return
	 */
	private static List<ComplexObject> populateComplexObjectList(){
		//Complex object List
		List<ComplexObject> complexObjectList = new ArrayList<ComplexObject>();

		//1
		ComplexObject complexObject1 = new ComplexObject();

		complexObject1.setString( com.Constant.CONST_COMPLEXOBJECT_STRING1 );

		List<String> stringList1 = new ArrayList<String>();
		stringList1.add( com.Constant.CONST_COMPLEXOBJECT_LIST_STRING_STRING1 );
		stringList1.add( com.Constant.CONST_COMPLEXOBJECT_LIST_STRING_STRING2 );
		complexObject1.setStringList( stringList1 );

		List<BeanC> beanCList1 = new ArrayList<BeanC>();
		BeanC beanC1 = new BeanC();
		beanC1.setString( com.Constant.CONST_BEANC1_STRING );
		beanCList1.add( beanC1 );
		BeanC beanC2 = new BeanC();
		beanC2.setString( com.Constant.CONST_BEANC2_STRING );
		beanCList1.add( beanC2 );
		complexObject1.setBeanCList( beanCList1 );

		complexObjectList.add( complexObject1 );

		//2
		ComplexObject complexObject2 = new ComplexObject();

		complexObject2.setString( com.Constant.CONST_COMPLEXOBJECT_STRING2 );
		List<String> stringList2 = new ArrayList<String>();
		stringList2.add( com.Constant.CONST_COMPLEXOBJECT_LIST_STRING_STRING3 );
		stringList2.add( com.Constant.CONST_COMPLEXOBJECT_LIST_STRING_STRING4 );
		complexObject2.setStringList( stringList2 );

		List<BeanC> beanCList2 = new ArrayList<BeanC>();
		BeanC beanC3 = new BeanC();
		beanC3.setString( com.Constant.CONST_BEANC3_STRING );
		beanCList2.add( beanC3 );
		BeanC beanC4 = new BeanC();
		beanC4.setString( com.Constant.CONST_BEANC4_STRING );
		beanCList2.add( beanC4 );
		complexObject2.setBeanCList( beanCList2 );

		complexObjectList.add( complexObject2 );
		return complexObjectList;
	}

	/**
	 * @return
	 */
	private static Set<ComplexObject> populateComplexObjectSet(){
		//Complex object Set
		Set<ComplexObject> complexObjectSet = new HashSet<ComplexObject>();

		//1
		ComplexObject complexObject1 = new ComplexObject();

		complexObject1.setString( com.Constant.CONST_COMPLEXOBJECT_STRING1 );

		List<String> stringList1 = new ArrayList<String>();
		stringList1.add( com.Constant.CONST_COMPLEXOBJECT_LIST_STRING_STRING1 );
		stringList1.add( com.Constant.CONST_COMPLEXOBJECT_LIST_STRING_STRING2 );
		complexObject1.setStringList( stringList1 );

		List<BeanC> beanCList1 = new ArrayList<BeanC>();
		BeanC beanC1 = new BeanC();
		beanC1.setString( com.Constant.CONST_BEANC1_STRING );
		beanCList1.add( beanC1 );
		BeanC beanC2 = new BeanC();
		beanC2.setString( com.Constant.CONST_BEANC2_STRING );
		beanCList1.add( beanC2 );
		complexObject1.setBeanCList( beanCList1 );

		complexObjectSet.add( complexObject1 );

		//2
		ComplexObject complexObject2 = new ComplexObject();

		complexObject2.setString( com.Constant.CONST_COMPLEXOBJECT_STRING2 );
		List<String> stringList2 = new ArrayList<String>();
		stringList2.add( com.Constant.CONST_COMPLEXOBJECT_LIST_STRING_STRING3 );
		stringList2.add( com.Constant.CONST_COMPLEXOBJECT_LIST_STRING_STRING4 );
		complexObject2.setStringList( stringList2 );

		List<BeanC> beanCList2 = new ArrayList<BeanC>();
		BeanC beanC3 = new BeanC();
		beanC3.setString( com.Constant.CONST_BEANC3_STRING );
		beanCList2.add( beanC3 );
		BeanC beanC4 = new BeanC();
		beanC4.setString( com.Constant.CONST_BEANC4_STRING );
		beanCList2.add( beanC4 );
		complexObject2.setBeanCList( beanCList2 );

		complexObjectSet.add( complexObject2 );

		return complexObjectSet;
	}


	/**
	 * @return
	 */
	private static List<BeanC> populateListOfBeanC(){
		List<BeanC> beanCList = new ArrayList<BeanC>();
		for( int i = 0; i < 10; i++ ){
			BeanC beanC = new BeanC();
			beanC.setString( String.valueOf( i ) );
			beanCList.add( i, beanC );
		}
		return beanCList;
	}

	/**
	 * @return
	 */
	private static List<BeanA> populateListOfBeanA(){
		List<BeanA> beanAList = new ArrayList<BeanA>();
		for( int i = 0; i < 10; i++ ){
			beanAList.add( i, populateBeanA() );
		}
		return beanAList;
	}


	/**
	 * @return
	 */
	private static Set<BeanA> populateSetOfBeanA(){
		Set<BeanA> beanASet = new HashSet<BeanA>( 10 );
		for( int i = 0; i < 10; i++ ){
			beanASet.add( populateBeanA() );
		}
		return beanASet;
	}

	/**
	 * @return
	 */
	private static SourceBean populateSourceBean(){
		SourceBean srcBean = new SourceBean();
		List<BeanA> beanAList = populateListOfBeanA();
		BeanA[] beanAArray = populateArrayOfBeanA();
		Set<BeanA> beanASet = populateSetOfBeanA();
		srcBean.setBeanAList( beanAList );
		srcBean.setBeanAArray( beanAArray );
		srcBean.setBeanASet( beanASet );
		return srcBean;
	}

	/**
	 * @param beanB
	 */
	private static void printArrayCopy( BeanB beanB ){
		System.out.println( "Printing the contents of the array of complex object:" );
		for( ComplexObject complexObject : beanB.getComplexObjectArray() ){
			System.out.println( "complexObject.getString()_1" + complexObject.getString() );
			for( String string : complexObject.getStringList() ){
				System.out.println( "complexObject.getStringList():_1" + string );
			}
			for( BeanC beanC : complexObject.getBeanCList() ){
				System.out.println( "complexObject.getBeanCList():_1" + beanC.getString() );
			}
		}
	}

	/**
	 * @param <T>
	 * @return
	 */
	private static void printBeanCArray( BeanC[] array ){
		if( !DevToolsUtils.isEmpty( array ) ){
			for( int i = 0; i < array.length; i++ ){
				BeanC beanC = array[ i ];
				System.out.println( "BeanC - Array Contents: " + beanC.getString() );
			}
		}
	}

	/**
	 * @param <T>
	 * @return
	 */
	private static void printBeanCList( List<BeanC> list ){
		if( !DevToolsUtils.isEmpty( list ) ){
			for( int i = 0; i < list.size(); i++ ){
				BeanC beanC = list.get( i );
				System.out.println( "BeanC - List Contents: " + beanC.getString() );
			}
		}
	}

	/**
	 * @param <T>
	 * @return
	 */
	private static void printBeanCSet( Set<BeanC> set ){
		if( !DevToolsUtils.isEmpty( set ) ){
			for( Iterator iterator = set.iterator(); iterator.hasNext(); ){
				BeanC beanC = (BeanC) iterator.next();
				System.out.println( "BeanC - Set Contents: " + beanC.getString() );
			}
		}
	}

	/**
	 * @param beanB
	 */
	private static void printListCopy( BeanB beanB ){
		System.out.println( "Printing the contents of the list of complex object:" );
		for( ComplexObject complexObject : beanB.getComplexObjectList() ){
			System.out.println( "complexObject.getString()_2" + complexObject.getString() );
			for( String string : complexObject.getStringList() ){
				System.out.println( "complexObject.getStringList():_2" + string );
			}
			for( BeanC beanC : complexObject.getBeanCList() ){
				System.out.println( "complexObject.getBeanCList():_2" + beanC.getString() );
			}
		}
	}

	/**
	 * @param beanB
	 */
	private static void printSetCopy( BeanB beanB ){
		System.out.println( "Printing the contents of the set of complex object:" );
		for( ComplexObject complexObject : beanB.getComplexObjectSet() ){
			System.out.println( "complexObject.getString()_3" + complexObject.getString() );
			for( String string : complexObject.getStringList() ){
				System.out.println( "complexObject.getStringList():_3" + string );
			}
			for( BeanC beanC : complexObject.getBeanCList() ){
				System.out.println( "complexObject.getBeanCList():_3" + beanC.getString() );
			}
		}
	}
	

	
	private static void cToA(){
		//List of typeA to List of TypeB
		List<BeanC> lC = populateListOfBeanC();
		Class mapperClass = BeanMapperFactory.getMapperClass( BeanC.class, BeanA.class );
		List<BeanA> beanAList = (List<BeanA>) CopyUtils.copy( lC, new ArrayList<BeanA>(), mapperClass );
		printBeanAList( beanAList );
		separateOutputs();

		/*//List of typeA to Set of TypeB
		Set<BeanC> beanCSet = (Set<BeanC>) CopyUtils.copy( populateListOfBeanA(), new HashSet<BeanC>(), BeanAToBeanCMapper.class );
		printBeanCSet( beanCSet );
		separateOutputs();

		//List of typeA to Array of TypeB
		BeanC[] beanCArray = (BeanC[]) CopyUtils.copy( populateListOfBeanA(), new BeanC[ populateArrayOfBeanA().length ], BeanAToBeanCMapper.class );
		printBeanCArray( beanCArray );
		separateOutputs();

		//Set of typeA to List of TypeB
		List<BeanC> beanCList2 = (List<BeanC>) CopyUtils.copy( populateBeanASet(), new ArrayList<BeanC>(), BeanAToBeanCMapper.class );
		printBeanCList( beanCList2 );
		separateOutputs();

		//Set of typeA to array of TypeB
		BeanC[] beanCArray2 = (BeanC[]) CopyUtils.copy( populateBeanASet(), new BeanC[ populateArrayOfBeanA().length ], BeanAToBeanCMapper.class );
		printBeanCArray( beanCArray2 );
		separateOutputs();

		//Set of typeA to set of TypeB
		Set<BeanC> beanCSet2 = (Set<BeanC>) CopyUtils.copy( populateBeanASet(), new HashSet<BeanC>(), BeanAToBeanCMapper.class );
		printBeanCSet( beanCSet2 );
		separateOutputs();

		//Array of typeA to array of TypeB
		BeanC[] beanCArray3 = (BeanC[]) CopyUtils.copy( populateArrayOfBeanA(), new BeanC[ populateArrayOfBeanA().length ], BeanAToBeanCMapper.class );
		printBeanCArray( beanCArray3 );
		separateOutputs();

		//Array of typeA to List of TypeB
		List<BeanC> beanCList3 = (List<BeanC>) CopyUtils.copy( populateArrayOfBeanA(), new ArrayList<BeanC>(), BeanAToBeanCMapper.class );
		printBeanCList( beanCList3 );
		separateOutputs();

		//List of typeA to Set of TypeB
		Set<BeanC> beanCSet3 = (Set<BeanC>) CopyUtils.copy( populateArrayOfBeanA(), new HashSet<BeanC>(), BeanAToBeanCMapper.class );
		printBeanCSet( beanCSet3 );	*/
	}
	
	private static void printBeanAList( List<BeanA> beanAList ){
		for( BeanA a : beanAList ){
			System.out.println( "str [" + a.getStr() + "]" );
		}
		
	}

	private static BeanC populateBeanC(){
		BeanC b = new BeanC();
		b.setString( "Rev Hi" );
		
		return b;
	}
}
