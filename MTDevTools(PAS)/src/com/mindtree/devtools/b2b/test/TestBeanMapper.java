/**
 * 
 */
package com.mindtree.devtools.b2b.test;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mindtree.devtools.b2b.sample.BeanA;
import com.mindtree.devtools.b2b.sample.BeanB;
import com.mindtree.devtools.b2b.sample.BeanC;
import com.mindtree.devtools.b2b.sample.ComplexObject;
import com.mindtree.devtools.b2b.sample.DestBean;
import com.mindtree.devtools.b2b.sample.FormVO;
import com.mindtree.devtools.b2b.sample.HttpRequest;
import com.mindtree.devtools.b2b.sample.LevelTwo;
import com.mindtree.devtools.b2b.sample.SourceBean;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;


/**
 * @author SMurthy
 *
 */
public class TestBeanMapper{

	/**
	 * @param args
	 */
	public static void main( String[] args ){
		//testBeanAToBeanCMapper();
		//testBean2BeanMapper();
		//testSrcDestBeanMapper();
		testRequestVOToFormMapper();
	}

	/**
	 * 
	 */
	private static void testBean2BeanMapper(){
		BeanA beanA = populateBeanA();
		//beanB = BeanAToBeanBMapper.mapBean( beanA, beanB );
		//beanB = BeanMapperFactory.getMapperInstance( BeanAToBeanBMapper.class ).mapBean( beanA, beanB );
		BeanB beanB = BeanMapper.map( beanA, BeanB.class );
		printMappedBeanInfo( beanB );
		
		
		BeanB beanB2 = populateBeanB();
		BeanA beanA2 = BeanMapper.map( beanB2, BeanA.class );
		//beanA2 = BeanMapperFactory.getMapperInstance( BeanAToBeanBMapperReverse.class ).mapBean( beanB2, beanA2 );
		printMappedBeanInfo( beanA2 );
	}

	private static void testRequestVOToFormMapper(){
		HttpRequest req = populateHttpRequest();
		FormVO formVO = BeanMapper.map( req, FormVO.class );
		System.out.println( formVO );
	}

	private static void testBeanAToBeanCMapper(){
		BeanA beanA = populateBeanA();
		/*beanA.setDate( new Date() );
		beanA.setBeanABoolean( false );
		beanA.setStr( "BeanAString" );*/
		
		BeanC retC = BeanMapper.map( beanA, BeanC.class );
		System.out.println( "BeanC.string [" + retC.getString() + "]" );
		//System.out.println( "BeanC.levelTwo.yes [" + retC.getLevelTwo().isYes() + "]" );
		
		BeanC c = populateBeanC(); 
		LevelTwo l2 = new LevelTwo();
		c.setString( "12/25/2034" );
		l2.setYes( false );
		c.setLevelTwo( l2 );
		
		BeanA retA = BeanMapper.map( c, BeanA.class );
		System.out.println( "BeanA.date [" + retA.getDate() + "]" );
		System.out.println( "BeanA.str [" + retA.getStr() + "]" );
		System.out.println( "BeanA.beanABoolean [" + retA.isBeanABoolean() + "]" );
	}

	/**
	 * 
	 */
	private static void testSrcDestBeanMapper(){
		SourceBean srcBean = populateSourceBean();
		DestBean destBean = BeanMapper.map( srcBean, DestBean.class );
		//destBean = SourceDestBeanMapper.mapBean( srcBean, destBean );
		//destBean = BeanMapperFactory.getMapperInstance( SourceDestBeanMapper.class ).mapBean( srcBean, destBean );
		System.out.println( "destBean List: " + destBean.getBeanCList().size() );
		System.out.println( "destBean Array: " + destBean.getBeanCArray().length );
		System.out.println( "destBean Se: " + destBean.getBeanCSet().size() );
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

	/**
	 * @return
	 */
	private static BeanA populateBeanA(){
		BeanA beanA = new BeanA();

		//Non-nested attributes
		beanA.setIntprimitive( 1 );
		beanA.setDate( new Date() );
		beanA.setStr( "This is BeanA.str" );
		beanA.setIntegerObject( 1 );
		beanA.setBeanABoolean( Boolean.TRUE );

		//Simple list
		List<String> strList = new ArrayList<String>();
		strList.add( "A" );
		strList.add( "B" );
		strList.add( "C" );
		beanA.setStringList( strList );
		
		String[] arr = { "12/23/2001", "12/24/2001", "08/24/2010", "01/31/2012" };
		beanA.setStrArray( arr );

		//Complex Object
		ComplexObject complexObject = new ComplexObject();
		complexObject.setString( "Jishnu" );
		
		SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
		
		complexObject.setDate( sdf.parse( "25/12/2011", new ParsePosition( 0 ) ) );
		beanA.setComplexObject( complexObject );

		//Complex object List
		List<ComplexObject> complexObjectList = new ArrayList<ComplexObject>();
		ComplexObject complexObject1 = new ComplexObject();
		complexObject1.setString( com.Constant.CONST_COMPLEOBJECT_LIST_STRING1 );
		List<String> stringList1 = new ArrayList<String>();
		stringList1.add( com.Constant.CONST_COMPLEOBJECT_LIST_DEEP_STRING1 );
		stringList1.add( com.Constant.CONST_COMPLEOBJECT_LIST_DEEP_STRING2 );
		complexObject1.setStringList( stringList1 );
		ComplexObject complexObject2 = new ComplexObject();
		complexObject2.setString( com.Constant.CONST_COMPLEOBJECT_LIST_STRING2 );
		List<String> stringList2 = new ArrayList<String>();
		stringList2.add( "CompleObject List deep String3" );
		stringList2.add( "CompleObject List deep String4" );
		complexObjectList.add( complexObject1 );
		complexObjectList.add( complexObject2 );
		complexObject2.setStringList( stringList2 );
		beanA.setComplexObjectList( complexObjectList );

		//Complex Object set
		Set<ComplexObject> complexObjectSet = new HashSet<ComplexObject>();
		ComplexObject complexObject3 = new ComplexObject();
		complexObject3.setString( "CompleObject Set String1" );
		ComplexObject complexObject4 = new ComplexObject();
		complexObject4.setString( "CompleObject Set String2" );
		complexObjectSet.add( complexObject3 );
		complexObjectSet.add( complexObject4 );
		beanA.setComplexObjectSet( complexObjectSet );

		ComplexObject[] complexObjectArray = new ComplexObject[ 2 ];
		ComplexObject complexObject5 = new ComplexObject();
		complexObject5.setString( "CompleObject array String1" );
		ComplexObject complexObject6 = new ComplexObject();
		complexObject6.setString( "CompleObject array String2" );
		complexObjectArray[ 0 ] = complexObject3;
		complexObjectArray[ 1 ] = complexObject3;
		beanA.setComplexObjectArray( complexObjectArray );
		
		LevelTwo lt = new LevelTwo(); lt.setYesString( "YES-STRING" );
		beanA.getStrLevelTwoMap().put( Integer.valueOf( "10" ), lt );

		return beanA;
	}
	
	/**
	 * @return
	 */
	private static BeanB populateBeanB(){
		BeanB bean = new BeanB();

		//Non-nested attributes
		bean.setIntprimitive( 1 );
		bean.setDate( new Date() );
		bean.setStr( "Suresh" );
		bean.setIntegerObject( 1 );

		//Simple list
		List<String> strList = new ArrayList<String>();
		strList.add( "A" );
		strList.add( "B" );
		strList.add( "C" );
		bean.setStringList( strList );

		//Complex Object
		ComplexObject complexObject = new ComplexObject();
		//complexObject.setString( "Jishnu" );
		BeanB beanB = new BeanB();
		beanB.setStr( "Wonderful" );
		complexObject.setBeanB( beanB );
		bean.setComplexObject( complexObject );

		//Complex object List
		List<ComplexObject> complexObjectList = new ArrayList<ComplexObject>();
		ComplexObject complexObject1 = new ComplexObject();
		complexObject1.setString( com.Constant.CONST_COMPLEOBJECT_LIST_STRING1 );
		List<String> stringList1 = new ArrayList<String>();
		stringList1.add( com.Constant.CONST_COMPLEOBJECT_LIST_DEEP_STRING1 );
		stringList1.add( com.Constant.CONST_COMPLEOBJECT_LIST_DEEP_STRING2 );
		complexObject1.setStringList( stringList1 );
		ComplexObject complexObject2 = new ComplexObject();
		complexObject2.setString( com.Constant.CONST_COMPLEOBJECT_LIST_STRING2 );
		List<String> stringList2 = new ArrayList<String>();
		stringList2.add( "CompleObject List deep String3" );
		stringList2.add( "CompleObject List deep String4" );
		complexObjectList.add( complexObject1 );
		complexObjectList.add( complexObject2 );
		complexObject2.setStringList( stringList2 );
		bean.setComplexObjectList( complexObjectList );

		//Complex Object set
		Set<ComplexObject> complexObjectSet = new HashSet<ComplexObject>();
		ComplexObject complexObject3 = new ComplexObject();
		complexObject3.setString( "CompleObject Set String1" );
		ComplexObject complexObject4 = new ComplexObject();
		complexObject4.setString( "CompleObject Set String2" );
		complexObjectSet.add( complexObject3 );
		complexObjectSet.add( complexObject4 );
		bean.setComplexObjectSet( complexObjectSet );

		ComplexObject[] complexObjectArray = new ComplexObject[ 2 ];
		ComplexObject complexObject5 = new ComplexObject();
		complexObject5.setString( "CompleObject array String1" );
		ComplexObject complexObject6 = new ComplexObject();
		complexObject6.setString( "CompleObject array String2" );
		complexObjectArray[ 0 ] = complexObject3;
		complexObjectArray[ 1 ] = complexObject3;
		bean.setComplexObjectArray( complexObjectArray );

		return bean;
	}
	
	private static BeanC populateBeanC(){
		BeanC bean = new BeanC();
		List<ComplexObject> complexObjectList = new com.mindtree.ruc.cmn.utils.List<ComplexObject>( ComplexObject.class );
		ComplexObject complexObject1 = new ComplexObject();
		complexObject1.setString( com.Constant.CONST_COMPLEOBJECT_LIST_STRING1 );
		List<String> stringList1 = new ArrayList<String>();
		stringList1.add( com.Constant.CONST_COMPLEOBJECT_LIST_DEEP_STRING1 );
		stringList1.add( com.Constant.CONST_COMPLEOBJECT_LIST_DEEP_STRING2 );
		complexObject1.setStringList( stringList1 );
		complexObject1.setDate( new Date() );
		
		ComplexObject complexObject2 = new ComplexObject();
		complexObject2.setString( com.Constant.CONST_COMPLEOBJECT_LIST_STRING2 );
		List<String> stringList2 = new ArrayList<String>();
		stringList2.add( "CompleObject 2 List deep String3" );
		stringList2.add( "CompleObject 2 List deep String4" );
		complexObject2.setStringList( stringList2 );
		complexObject2.setDate( new Date() );
		
		ComplexObject complexObject3 = new ComplexObject();
		complexObject3.setString( "CompleObject List String3" );
		List<String> stringList3 = new ArrayList<String>();
		stringList1.add( "CompleObject 3 List deep String1" );
		stringList1.add( "CompleObject 3 List deep String2" );
		complexObject3.setStringList( stringList3 );
		
		ComplexObject complexObject4 = new ComplexObject();
		complexObject4.setString( "CompleObject List String4" );
		List<String> stringList4 = new ArrayList<String>();
		stringList1.add( "CompleObject 4 List deep String1" );
		stringList1.add( "CompleObject 4 List deep String2" );
		complexObject4.setStringList( stringList4 );
		
		complexObjectList.add( complexObject1 );
		complexObjectList.add( complexObject2 );
		complexObjectList.add( complexObject3 );
		complexObjectList.add( complexObject4 );
		bean.setComplexObjectList( (com.mindtree.ruc.cmn.utils.List<ComplexObject>) complexObjectList );

		ComplexObject[] complexObjectArray = new ComplexObject[ 4 ];
		ComplexObject complexObject5 = new ComplexObject();
		complexObject5.setString( "This is BeanC.complexObjectArray[ 0 ].str" );
		ComplexObject complexObject6 = new ComplexObject();
		complexObject6.setString( "This is BeanC.complexObjectArray[ 1 ].str" );
		ComplexObject complexObject7 = new ComplexObject();
		complexObject7.setString( "This is BeanC.complexObjectArray[ 2 ].str" );
		ComplexObject complexObject8 = new ComplexObject();
		complexObject8.setString( "This is BeanC.complexObjectArray[ 3 ].str" );
		complexObjectArray[ 0 ] = complexObject5;
		complexObjectArray[ 1 ] = complexObject6;
		complexObjectArray[ 2 ] = complexObject7;
		complexObjectArray[ 3 ] = complexObject8;
		bean.setComplexObjectArray( complexObjectArray );

		return bean;
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
	private static void printMappedBeanInfo( BeanB beanB ){
		System.out.println( "####Contents of BeanB after copying####" );
		System.out.println( "intprimitive [" + beanB.getIntprimitive() + "]" );
		System.out.println( "complexObject.date [" + beanB.getComplexObject().getDate() + "]" );
		System.out.println( "complexObject.str [" + beanB.getComplexObject().getString() + "]" );
		System.out.println( "str [" + beanB.getStr() + "]" );
		System.out.println( "String list:" );
		for( String str : beanB.getStringList() ){
			System.out.println( "\t" + str );
		}
		System.out.println( "Complex Object list:" );
		for( ComplexObject complexObject : beanB.getComplexObjectList() ){
			System.out.println( complexObject.getString() );
			for( String string : complexObject.getStringList() ){
				System.out.println( "\t" + string );
			}
		}
		System.out.println( "Complex Object Set:" );
		for( ComplexObject complexObject : beanB.getComplexObjectSet() ){
			System.out.println( "\t" + complexObject.getString() );
		}
		System.out.println( "Complex Object Array:" );
		for( ComplexObject complexObject : beanB.getComplexObjectArray() ){
			System.out.println( "\t" + complexObject.getString() );
		}
		System.out.println( "integerObject [" + beanB.getIntegerObject() + "]" );
		System.out.println( "complexObject.beanB.str [" + beanB.getComplexObject().getBeanB().getStr() + "]" );
	}
	

	/**
	 * @param bean
	 */
	private static void printMappedBeanInfo( BeanA bean ){
		System.out.println();
		System.out.println( "####Contents of BeanA after copying####" );
		System.out.println( "intprimitive [" + bean.getIntprimitive() + "]" );
		System.out.println( "complexObject.date [" + bean.getComplexObject().getDate() + "]" );
		System.out.println( "date [" + bean.getDate() + "]" );
		System.out.println( "complexObject.str [" + bean.getComplexObject().getString() + "]" );
		System.out.println( "str [" + bean.getStr() + "]" );
		System.out.println( "String list:" );
		for( String str : bean.getStringList() ){
			System.out.println( "\t" + str );
		}
		System.out.println( "Complex Object list:" );
		for( ComplexObject complexObject : bean.getComplexObjectList() ){
			System.out.println( complexObject.getString() );
			for( String string : complexObject.getStringList() ){
				System.out.println( "\t" + string );
			}
		}
		System.out.println( "Complex Object Set:" );
		for( ComplexObject complexObject : bean.getComplexObjectSet() ){
			System.out.println( "\t" + complexObject.getString() );
		}
		System.out.println( "Complex Object Array:" );
		for( ComplexObject complexObject : bean.getComplexObjectArray() ){
			System.out.println( "\t" + complexObject.getString() );
		}
		System.out.println( "integerObject [" + bean.getIntegerObject() + "]" );
		System.out.println( "complexObject.beanB.str [" + bean.getComplexObject().getBeanB().getStr() + "]" );
	}
	
	private static HttpRequest populateHttpRequest(){
		HttpRequest request = new HttpRequest();
		request.addParameter( "uwq[0]", "Question 0" );
		request.addParameter( "uwq[1]", "Question 1" );
		request.addParameter( "uwq[2]", "Question 2" );
		request.addParameter( "uwq[3]", "Question 3" );
		
		request.addParameter( "yesso", "on" );
		request.addParameter( "date", "12/24/2019" );
		
		return request;
		
	}
}
