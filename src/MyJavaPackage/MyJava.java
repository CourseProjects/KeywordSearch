package MyJavaPackage;
/***********************************************
*参考示例代码FileFinder.java， FilesTest.java.
*可以搜索某个文件夹及其子文件夹内的.txt文件中是否存在指定关键词
*关键词在String s中，文件夹在fileDir中
*由于字符编码是"US-ASCII"，因此只能查找ASCII码，汉字不可以查找
*修改后字符编码是"gbk"，则可以显示汉字
*只支持未编码的格式，如.txt,.java等，不支持编码格式如word,ppt
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
	private static	String s = null;		//要搜索的关键词
	private static boolean flag = false;	//标志文件内是否存在关键词
	private static int raws = 0;			//记录文件内的行数
	private static String sta = null;		//在JTextArea中显示的字符串
	private static String ext = null;		//设置后缀名
	private PathMatcher matcher;
	public ArrayList<Path> foundPaths=new ArrayList<>();

	public static void setKeyword(String str){		//设置要查找的关键词
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

	public MyJava(String pattern){		//支持通配符，即可以选择某一后缀名的文件
	    matcher=FileSystems.getDefault().getPathMatcher("glob:"+pattern);	
	}
	
	private static String getPathName(){	//显示选择文件夹对话框，获取文件夹路径
		JFileChooser pathChooser = new JFileChooser();
	    pathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY );
//	    //设置“打开文件夹对话框”的默认路径---------没有增加设置默认路径的选项
//	    pathChooser.setCurrentDirectory(
//	    		Paths.get("F:\\JavaFileTest").toFile()); 
	    //设置对话框标题
	    pathChooser.setDialogTitle("选择查找的文件夹");
	    //设置确定按钮名
	    pathChooser.setApproveButtonText("确定");  
	    //显示打开文件对话框
	    int result = pathChooser.showOpenDialog( null );
	    //按下“取消”按钮，返回空
	    if ( result == JFileChooser.CANCEL_OPTION )
	         return null;

	    String pathName = pathChooser.getSelectedFile().getAbsolutePath();
	    return pathName;
	}
//--------------------打开选择文件夹对话框-----------------------------	
	public static void selectDictionary() {
		String pathName = null;			//选择的路径名
		Path pathDir = null;			//选择的路径
		if(s.equals("")){					//如果关键字为空，则提示输入关键字
			JOptionPane.showMessageDialog( null,  
					"请重新输入关键字",
                    "输入有误",
                    JOptionPane.INFORMATION_MESSAGE );
			return ;
		}
		pathName = MyJava.getPathName();
		if(pathName == null){			//如果取消，则退出程序	
			return ;
		}
		pathDir=Paths.get(pathName);
		MyJava finder = new MyJava(ext);		//设置后缀名
		Charset charset = Charset.forName("gbk");	//设置编码格式
		try {
			Files.walkFileTree(pathDir, finder);	//遍历文件夹
			ArrayList<Path> foundFiles=finder.foundPaths;
			if(foundFiles.size()>0){	//如果文件夹内有文件，则进行搜索	
				flag = false ;			//每次搜索文件时，将标志位置为false
				sta = null ;			//每次搜索文件时，将sta指向null
				for (Path path : foundFiles) {		//搜索每一个文件夹的内容
					List<String> linesRead = null;
			        try {			//读出文件的内容
			            linesRead = Files.readAllLines(path, charset);
			        } catch (IOException ex) {
			            ex.printStackTrace();
			        }
			        raws = 0;		//行数清零
			        if (linesRead != null) {
			            for (String line : linesRead) {
			            	raws++;
			            	if(line.indexOf(s)>=0){
			            		
				    			sta = path.toRealPath(
				    					LinkOption.NOFOLLOW_LINKS).toString();
			            		
			    				flag = true;	//如果找到关键词，则输出，flag=true
			    				MyJavaGUI.setRadioButton(sta);
			    				break;
			            	}
			            }
			        }
				}
//	            MyJavaGUI.setTextArea(sta);
				if(flag == false){				//如果没找到关键词，输出提示
					JOptionPane.showMessageDialog( null,  
							"文件夹中没有含指定关键词的文件",
		                    "没有找到所需文件",
		                    JOptionPane.INFORMATION_MESSAGE );
					return ;
				}
			}
			else {			//如果文件夹下没有文件，输出提示
				JOptionPane.showMessageDialog( null,  
						"文件夹中没有对应后缀的文件！！",
	                    "没有文件",
	                    JOptionPane.INFORMATION_MESSAGE );
				return ;
			}
			
		} catch (IOException e) {
		
			e.printStackTrace();
		}

	}

}
