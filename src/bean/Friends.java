package bean;

public class Friends {
         private String owner_name;
         private String group_name;
         private String friend_name;
         private String state;
         public Friends(){
        	 
         }
         public Friends(String a,String b,String c,String d){
        	 owner_name=a;
        	 group_name=b;
        	 friend_name=c;
        	 state=d;
         }
         public String getOwner(){
        	 return owner_name;
         }
         public void setOwner(String a){
        	 owner_name=a;
         }
         public String getGroup(){
        	 return group_name;
         }
         public void setGroup(String a){
        	 group_name=a;
         }
         public String getFriend(){
        	 return friend_name;
         }
         public void setFriend(String a){
        	 friend_name=a;
         }
         public String getState(){
        	 return state;
         }
         public void setState(String a){
        	 state=a;
         }
         
}
