package com.rsaame.pas.b2b.ws.batch.tasks;

public enum RsaSection {
	PROPERTY_ALL_RISKS("Property All Risks",1,2){

		@Override
		public String getSectionName() {
			return sectionName;
		}

		@Override
		public int getSectionId() {
			return sectionId;
		}

		@Override
		public int getClassCode() {
			return classCode;
		}
		
	},
	PUBLIC_LIABLITY("Public liability",6,7){

		@Override
		public String getSectionName() {
			return sectionName;
		}

		@Override
		public int getSectionId() {
			return sectionId;
		}

		@Override
		public int getClassCode() {
			return classCode;
		}
		
	},
	WORKMEN_COMPENSATION("Workmen Compensation",7,7){

		@Override
		public String getSectionName() {
			return sectionName;
		}

		@Override
		public int getSectionId() {
			return sectionId;
		}

		@Override
		public int getClassCode() {
			return classCode;
		}
		
	},
	BUSINESS_INTERRUPTION("Business Interruption",2,2){

		@Override
		public String getSectionName() {
			return sectionName;
		}

		@Override
		public int getSectionId() {
			return sectionId;
		}

		@Override
		public int getClassCode() {
			return classCode;
		}
		
	},
	ELECTRONIC_EQUIPMENT("Electronic Equipment",5,2){

		@Override
		public String getSectionName() {
			return sectionName;
		}

		@Override
		public int getSectionId() {
			return sectionId;
		}

		@Override
		public int getClassCode() {
			return classCode;
		}
		
	},
	MACHINERY_BREAKDOWN("Machinery Breakdown",3,2){

		@Override
		public String getSectionName() {
			return sectionName;
		}

		@Override
		public int getSectionId() {
			return sectionId;
		}

		@Override
		public int getClassCode() {
			return classCode;
		}
		
	},
	DETERIORATION_OF_STOCK("Deterioration Of Stock",4,2){

		@Override
		public String getSectionName() {
			return sectionName;
		}

		@Override
		public int getSectionId() {
			return sectionId;
		}

		@Override
		public int getClassCode() {
			return classCode;
		}
		
	},
	TRAVEL("Travel (Baggage)",10,5){

		@Override
		public String getSectionName() {
			return sectionName;
		}

		@Override
		public int getSectionId() {
			return sectionId;
		}

		@Override
		public int getClassCode() {
			return classCode;
		}
		
	},
	MONEY("Money",8,5){

		@Override
		public String getSectionName() {
			return sectionName;
		}

		@Override
		public int getSectionId() {
			return sectionId;
		}

		@Override
		public int getClassCode() {
			return classCode;
		}
		
	},
	FIDELITY_GUARANTEE("Fidelity Guarantee",9,5){

		@Override
		public String getSectionName() {
			return sectionName;
		}

		@Override
		public int getSectionId() {
			return sectionId;
		}

		@Override
		public int getClassCode() {
			return classCode;
		}
		
	},
	GROUP_PERSONAL_ACCIDENT("Group Personal Accident",12,5){

		@Override
		public String getSectionName() {
			return sectionName;
		}

		@Override
		public int getSectionId() {
			return sectionId;
		}

		@Override
		public int getClassCode() {
			return classCode;
		}
		
	};
	
	
	public final String sectionName;
	public final int sectionId;
	public final int classCode;
	
	RsaSection(String sectionName,int sectionId,int classCode){
		this.sectionName=sectionName;
		this.sectionId=sectionId;
		this.classCode=classCode;
	}
	
	public abstract String getSectionName();
	public abstract int getSectionId();
	public abstract int getClassCode();
	
	
	
	

}
