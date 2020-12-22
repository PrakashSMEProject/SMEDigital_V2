package com.rsaame.pas.policy.dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class SPConvPkgQuoToPol extends StoredProcedure {
    
    private static final String SQL = "Pro_Conv_Pkg_Quo_To_Pol";

    public SPConvPkgQuoToPol( DataSource ds ) {
        setDataSource( ds );
        setFunction(false);
        setSql(SQL);
     
        declareParameter(new SqlParameter("AI_QUOTE_ID",OracleTypes.NUMBER));
        declareParameter(new SqlParameter("AI_CLIENT",OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("AI_USER_ID",OracleTypes.NUMBER));
        declareParameter(new SqlParameter("AI_POLICY_TYPE",OracleTypes.NUMBER));
        
        declareParameter(new SqlOutParameter("AO_POL_NO", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter("AO_ERR_TEXT", OracleTypes.VARCHAR ));
        compile();
    }

    public Map execute() {
        // the 'sysdate' sproc has no input parameters, so an empty Map is supplied...
    	Map inputs = new HashMap();
    	inputs.put("AI_QUOTE_ID", 392010);
    	inputs.put("AI_CLIENT", "OITC");
    	inputs.put("AI_USER_ID",237);
    	inputs.put("AI_POLICY_TYPE", 50 );
        return super.execute(inputs);
    }
}

