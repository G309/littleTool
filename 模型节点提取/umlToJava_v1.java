import java.io.*;
import java.util.*;
import java.lang.*;
class umlToJava_v0{
  
   int filerank = 0;
   public int getLetterNum(String string, String a){
      int pos = -2;
      int n = 0;
      while (pos != -1) {
        if (pos == -2) {
            pos = -1;
        }
        pos = string.indexOf(a, pos + 1);
        if (pos != -1) {
            n++;
        }
    }
      return n;
   }
   public void catchFile(BufferedWriter writer1,String d,String fileN){
     try{
        String fileName = d+"//"+fileN+".txt";
        String[] strdeep = new String[30];
        File file1 = new File(fileName);
        if(file1.exists()){
           BufferedReader reader1 = new BufferedReader(new FileReader(file1));
           String tempString1;
           int count = 0;
           while((tempString1 = reader1.readLine())!= null){
                 strdeep[count++] = tempString1;
                 System.out.println(fileN+"->"+tempString1);
           }
    
           for(int j = 0;j <strdeep.length;j++){
                if(strdeep[j]!=null){
                   String s="";
                   for (int k = filerank;k<getLetterNum(strdeep[j],".");k++)
                       s += " ";
                   writer1.write(s+strdeep[j]+"\r\n");
                   catchFile(writer1,d,strdeep[j]);             
                }
                else
                   j=strdeep.length;
           }
           reader1.close();
        }
     }catch(Exception ex){
        ex.printStackTrace();
     }
  }
  public void openAllFind(String dir,String[] filesName,String nodeName){
     String tempString2 = null;
     BufferedReader reader2; 
     for(int i = 0; i< filesName.length;i++){
        try{
            File file2 = new File(dir+"//"+filesName[i]);
            if(file2.exists()){
               reader2 = new BufferedReader(new FileReader(file2));
               while((tempString2 = reader2.readLine())!= null){
                  if(tempString2.equals(nodeName)){
                     System.out.println("为叶结点："+nodeName);
                     break;
                  }
               }
               reader2.close();
            }

        }catch(Exception ex){
           ex.printStackTrace();
        }
     }
  }
   public void findFirst(String dir,String nodeName,BufferedWriter writer){
     int flagFirst = -1;
     File file = new File(dir);
     String[] files = file.list();
     String nodeNamef = nodeName+".txt";
     for(int i = 0; i < files.length; i++ ){
        String strArray[] = files[i].split("\\\\");
        //搜索到以该nodeName为名的txt文件，之后打开该文件并提取后续内容即可
        int len = strArray.length;
        if(strArray[len-1].equals(nodeNamef)){
             System.out.println("找到该节点，整体文件名为："+files[i]);
             try{
                 writer.write(nodeName+"\r\n");
                 flagFirst = 1;
                 this.filerank = getLetterNum(nodeName,".");
                 catchFile(writer,dir,nodeName);
                 i = files.length;
             }catch(Exception ex){
               ex.printStackTrace();
             }
        }
     }
      //未搜索到以该nodeName为名的txt，则说明其位于某一文件夹下，进行打开各个文件穷尽搜索，直至找到break
     if (flagFirst == -1){
         openAllFind(dir,files,nodeName);
     }
  }
   public static void main(String [] args){
     try{
          BufferedWriter writer = new BufferedWriter(new FileWriter("E://uml_resultList.txt"));
          umlToJava_v0 f1=new umlToJava_v0();
          f1.findFirst("E://magic review项目//后台设计//UML","realtimeAspect.processor",writer); 
          writer.close();
     }catch(Exception ex){
          ex.printStackTrace();
     }
   }
}