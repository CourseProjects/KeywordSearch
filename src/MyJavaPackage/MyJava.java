package MyJavaPackage;
/***********************************************
*�ο�ʾ������FileFinder.java�� FilesTest.java.
*��������ĳ���ļ��м������ļ����ڵ�.txt�ļ����Ƿ����ָ���ؼ���
*�ؼ�����String s�У��ļ�����fileDir��
*�����ַ�������"US-ASCII"�����ֻ�ܲ���ASCII�룬���ֲ����Բ���
*�޸ĺ��ַ�������"gbk"���������ʾ����
*ֻ֧��δ����ĸ�ʽ����.txt,.java�ȣ���֧�ֱ����ʽ��word,ppt
***********************************************/

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import MyJavaGUIPackage.MyJavaGUI;



public class MyJava extends SimpleFileVisitor<Path> {
	private static	String s = null;		//Ҫ�����Ĺؼ���
	private static boolean flag = false;	//��־�ļ����Ƿ���ڹؼ���
	private static int raws = 0;			//��¼�ļ��ڵ�����
	private static String sta = null;		//��JTextArea����ʾ���ַ���
	private static String ext = null;		//���ú�׺��
	private PathMatcher matcher;
	public ArrayList<Path> foundPaths=new ArrayList<>();

	public static void setKeyword(String str){		//����Ҫ���ҵĹؼ���
		s = str;
	}
	public static void setExt(String ext) {
		MyJava.ext = new String(ext);
	}
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
			throws IOException {
		Path name=file.getFileName();
		if(matcher.matches(name)){
			foundPaths.add(file);
		}
		return FileVisitResult.CONTINUE;
	}

	public MyJava(String pattern){		//֧��ͨ�����������ѡ��ĳһ��׺�����ļ�
	    matcher=FileSystems.getDefault().getPathMatcher("glob:"+pattern);	
	}
	
	private static String getPathName(){	//��ʾѡ���ļ��жԻ��򣬻�ȡ�ļ���·��
		JFileChooser pathChooser = new JFileChooser();
	    pathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY );
//	    //���á����ļ��жԻ��򡱵�Ĭ��·��---------û����������Ĭ��·����ѡ��
//	    pathChooser.setCurrentDirectory(
//	    		Paths.get("F:\\JavaFileTest").toFile()); 
	    //���öԻ������
	    pathChooser.setDialogTitle("ѡ����ҵ��ļ���");
	    //����ȷ����ť��
	    pathChooser.setApproveButtonText("ȷ��");  
	    //��ʾ���ļ��Ի���
	    int result = pathChooser.showOpenDialog( null );
	    //���¡�ȡ������ť�����ؿ�
	    if ( result == JFileChooser.CANCEL_OPTION )
	         return null;

	    String pathName = pathChooser.getSelectedFile().getAbsolutePath();
	    return pathName;
	}
//--------------------��ѡ���ļ��жԻ���-----------------------------	
	public static void selectDictionary() {
		String pathName = null;			//ѡ���·����
		Path pathDir = null;			//ѡ���·��
		if(s.equals("")){					//����ؼ���Ϊ�գ�����ʾ����ؼ���
			JOptionPane.showMessageDialog( null,  
					"����������ؼ���",
                    "��������",
                    JOptionPane.INFORMATION_MESSAGE );
			return ;
		}
		pathName = MyJava.getPathName();
		if(pathName == null){			//���ȡ�������˳�����	
			return ;
		}
		pathDir=Paths.get(pathName);
		MyJava finder = new MyJava(ext);		//���ú�׺��
		Charset charset = Charset.forName("gbk");	//���ñ����ʽ
		try {
			Files.walkFileTree(pathDir, finder);	//�����ļ���
			ArrayList<Path> foundFiles=finder.foundPaths;
			if(foundFiles.size()>0){	//����ļ��������ļ������������	
				flag = false ;			//ÿ�������ļ�ʱ������־λ��Ϊfalse
				sta = null ;			//ÿ�������ļ�ʱ����staָ��null
				for (Path path : foundFiles) {		//����ÿһ���ļ��е�����
					List<String> linesRead = null;
			        try {			//�����ļ�������
			            linesRead = Files.readAllLines(path, charset);
			        } catch (IOException ex) {
			            ex.printStackTrace();
			        }
			        raws = 0;		//��������
			        if (linesRead != null) {
			            for (String line : linesRead) {
			            	raws++;
			            	if(line.indexOf(s)>=0){
			            		
				    			sta = path.toRealPath(
				    					LinkOption.NOFOLLOW_LINKS).toString();
			            		
			    				flag = true;	//����ҵ��ؼ��ʣ��������flag=true
			    				MyJavaGUI.setRadioButton(sta);
			    				break;
			            	}
			            }
			        }
				}
//	            MyJavaGUI.setTextArea(sta);
				if(flag == false){				//���û�ҵ��ؼ��ʣ������ʾ
					JOptionPane.showMessageDialog( null,  
							"�ļ�����û�к�ָ���ؼ��ʵ��ļ�",
		                    "û���ҵ������ļ�",
		                    JOptionPane.INFORMATION_MESSAGE );
					return ;
				}
			}
			else {			//����ļ�����û���ļ��������ʾ
				JOptionPane.showMessageDialog( null,  
						"�ļ�����û�ж�Ӧ��׺���ļ�����",
	                    "û���ļ�",
	                    JOptionPane.INFORMATION_MESSAGE );
				return ;
			}
			
		} catch (IOException e) {
		
			e.printStackTrace();
		}

	}

}
