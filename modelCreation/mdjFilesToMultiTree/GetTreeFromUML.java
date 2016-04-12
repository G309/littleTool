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
	public void buildSubTreeKey(JSONArray jarray,int pos,int len,BufferedWriter writer1,TreeNode parent){
		if(visited[pos] == -1) {
			JSONObject obj = jarray.getJSONObject(pos);
			try{
				writer1.write(getClassName(obj)+"\r\n");
				TreeNode child=new TreeNode(getClassName(obj));
				parent.addchild(child);
				int attributesNum = obj.getJSONArray("attributes").length();
				for(int j = 0;j<attributesNum;j++){
					//��ȡ���и����Ե�����
					String attributesName = obj.getJSONArray("attributes").getJSONObject(j).getString("name");
					writer1.write(getClassName(obj)+" : "+attributesName+"\r\n");
					//System.out.println( getClassName(obj)+" : "+attributesName);
					int findresult = attributeIsClass(attributesName,jarray,len);
					if( findresult !=-1){
						buildSubTreeKey(jarray,findresult,len,writer1,child);
					}
					else{
						TreeNode subchild = new TreeNode(attributesName);
						child.addchild(subchild);
					}
				}
				visited[pos] = 1;
			}catch (Exception e){
			}	
		}
	}
	

	public static void main(String[] args) throws IOException{
		TreeNode root=new TreeNode("root");
		Tree modelTree=new Tree(root);
		GetTreeFromUML gettreefromuml = new GetTreeFromUML();
		BufferedWriter writer = new BufferedWriter(new FileWriter("E://uml_resultList.txt"));
		File sourcefile = new File("E:\\UMLJson0.json");
		JSONObject jsonObject = new JSONObject( new JSONTokener(new FileReader(sourcefile))); 
		JSONArray jsonArray = jsonObject.getJSONArray("ownedElements").getJSONObject(0).getJSONArray("ownedElements");
		
		//��ȡ����UMLģ�Ͱ������������
		int classNum = jsonArray.length()-1;
		
		for(int i = 1;i<=classNum;i++){	
			gettreefromuml.buildSubTreeKey(jsonArray,i,classNum,writer,root);
		} 
		writer.close();
		TreeNode st = modelTree.findNode(root,"SCMP");
		if(st!=null){
			modelTree.bianli(st);
		}
		else{
			System.out.println("�����ڸýڵ㣡����");
		}
	}
}
	

