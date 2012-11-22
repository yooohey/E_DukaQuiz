package sample.stampLally;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.os.Environment;

public class SdcardToWrite {
	
	private ArrayList<String> arrayList= new ArrayList<String>();
	private String filePath;
	
	public  SdcardToWrite(String[] str){
		for(String str2:str){
			this.arrayList.add(str2);
		}
	}
	
	public SdcardToWrite(String str){
		this.arrayList.add(str);
	}
	
	public SdcardToWrite(ArrayList<String> arrayList){
		this.arrayList = arrayList;
	}

	public String getFileName(){
		return this.filePath;
	}
	
	public void outFile(){
		//SD�J�[�h�̃f�B���N�g���p�X
		File sdcardPath = new File(Environment.getExternalStorageDirectory().getPath()+ "/E_zuka_stamp/");
		
		//�t�@�C�����p�t�H�[�}�b�g
		Date todya = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd_hhmmss");

		//�p�X��؂�悤�Z�p���[�^
		String fs = File.separator;

		//�e�L�X�g�t�@�C���ۑ���̃t�@�C���p�X
		String filePath = sdcardPath + fs + dateFormat.format(todya)+ ".txt";

		//�t�H���_���Ȃ���΍쐬
		if(!sdcardPath.exists()){
			sdcardPath.mkdir();
		}
						
		try{
			Integer i =1;
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filePath)));
			for(String str:this.arrayList){
				pw.write(String.format("%02d_%s\n",i,str));
				pw.flush();
				i++;
				
			}
			
			
			this.filePath = filePath;
			
			pw.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
