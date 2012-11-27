package sample.postquiz;

public enum DBColumn {
	
	mode("mode"),statusCode("statuscode"),_id("_id"),userName("username"),userKey("userkey"),quesiton("question"),quizCode("quizCode"),genre("genre"),answer("answer"),dammy1("dammy1"),dammy2("dammy2"),dammy3("dammy3"),image("image"),correctCount("correctcount"),charengeCount("charengecount"),good("good"),created_at("created_at"),updated_at("updated_at");
	private String columnName;
	
	private DBColumn(String str){
		this.columnName = str;
	}
	
	public String getColumnName(){
		return this.columnName;
	}

}
