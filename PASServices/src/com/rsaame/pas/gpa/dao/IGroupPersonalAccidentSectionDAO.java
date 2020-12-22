package com.rsaame.pas.gpa.dao;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author m1019834
 *
 */
public interface IGroupPersonalAccidentSectionDAO{

/**
* Abstract method for loading GPA specific details.
* @param baseVO it will accept and have to set baseVO
* @return BaseVO
*/
BaseVO loadGroupPersonalAccidentSection(BaseVO baseVO);

/**
* Abstract method for saving GPA specific details.
* @param baseVO it will accept and have to set baseVO
* @return BaseVO
*/
BaseVO saveGroupPersonalAccidentSection(BaseVO baseVO);
}
