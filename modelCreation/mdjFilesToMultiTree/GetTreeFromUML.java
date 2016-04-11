import java.io.*;
import java.util.*;
import java.io.FileReader;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


public class GetTreeFromUML {
	
	private int[] visited;//��ʶ��index��Ӧ������Ϊ���ڵ�������ѹ�����ɣ�visited=1,��ʶ��ɣ�visited = -1,��ʶδ��ɡ�
	
	public GetTreeFromUML(){
		visited = new int[255];
		for(int v=0;v<255;v++){
			visited[v] = -1;
		}
	}
	
	//��ȡ�������
	public String getClassName(JSONObject jobj){
		String className = jobj.getString("name");
		//System.out.println(className);
		return className;
	}
		
	/******�ж������������һ������������ǣ�����"Match is OK"�����򣬷���"Match Failed"******/
	/******atName�������жϵ���������len������������UML����������� ****************************/
	
	public int attributeIsClass(String atName,JSONArray jarray,int len){
		for(int k=1;k<=len;k++){
			JSONObject obj = jarray.getJSONObject(k);
			if(getClassName(obj).equals(atName)){
				//System.out.println(getClassName(obj));
				return k;
			}
		}
		return -1;
	}
	
	/********����������ķ�ʽ�ݹ���Ҳ�������********************************/
	/********pos���������������Զ�Ӧ��������λ�ã�len��������UML���ڵ�����*******************************/
	public void buildSubTree(JSONArray jarray,int pos,int len){
		if(visited[pos] == -1){
			JSONObject obj = jarray.getJSONObject(pos);
			try{
				int attributesNum = obj.getJSONArray("attributes").length();
				for(int j = 0;j<attributesNum;j++){
					//��ȡ���и����Ե�����
					String attributesName = obj.getJSONArray("attributes").getJSONObject(j).getString("name");
					System.out.println( getClassName(obj)+" : "+attributesName);
					int findresult = attributeIsClass(attributesName,jarray,len);
					if( findresult !=-1){
						buildSubTree(jarray,findresult,len);
					}
				}
				visited[pos] = 1;
			}catch (Exception e){
			}	
		}
	}
	
	public static void main(String[] args) throws IOException{
		GetTreeFromUML gettreefromuml = new GetTreeFromUML();
		
		File sourcefile = new File("E:\\UMLJson0.json");
		JSONObject jsonObject = new JSONObject( new JSONTokener(new FileReader(sourcefile))); 
		JSONArray jsonArray = jsonObject.getJSONArray("ownedElements").getJSONObject(0).getJSONArray("ownedElements");
		
		//��ȡ����UMLģ�Ͱ������������
		int classNum = jsonArray.length()-1;
		
		for(int i = 1;i<=classNum;i++){
			gettreefromuml.buildSubTree(jsonArray,i,classNum);
			//System.out.println(gettreefromuml.getClassName(obj) +" : "+ attributesName);
		} 
		
	}
	

}
