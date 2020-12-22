package com.rsaame.pas.vo.bus;

/**
 * This interface represents an identifier in a VO.
 */
public interface IVOId{
	public Object getId();
	public void setId( Object id );
	
	/**
	 * Checks if the passed risk is the same as the one present in this instance of the VO.
	 * @param id The <code>id</code> to be compared with the <code>id</code> of this instance
	 * @return <code>true</code> if same, <code>false</code> if not.
	 */
	public boolean compareId( Long id );
}
