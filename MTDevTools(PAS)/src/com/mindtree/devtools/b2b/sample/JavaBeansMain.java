package com.mindtree.devtools.b2b.sample;

import java.util.List;

import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.Utils;

public class JavaBeansMain{

	/**
	 * @param args
	 */
	public static void main( String[] args ){
		FormVO formVO = new FormVO();
		Object o = BeanUtils.initializeBeanField( "levelTwo.uwq", formVO );
		System.out.println( o );
		
		UWQuestion[] uwq = (UWQuestion[]) o;
		Object o2 = Utils.newInstance( uwq.getClass().getComponentType() );
		System.out.println( o2 );
		
		o = BeanUtils.initializeBeanField( "levelTwo.uwqList", formVO );
		System.out.println( o );
		
		List<UWQuestion> uwqList = (List<UWQuestion>) o;
		o2 = Utils.newInstance( uwqList.getClass().getComponentType() );
		System.out.println( o2 );
		
		LevelTwo lt = new LevelTwo();
		lt.setYes( true );
		formVO.setLevelTwo( lt );
		System.out.println( "Deep field [levelTwo.yes]=[" + BeanUtils.getDeepFieldBean( "levelTwo.yes", formVO ) + "]" );
	}

}
