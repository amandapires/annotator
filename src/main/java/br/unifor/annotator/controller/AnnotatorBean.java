package br.unifor.annotator.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;

@ManagedBean
@ViewScoped
public class AnnotatorBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<String> columns;
	private List<Map<String, Object>> rows;
	



	private Scanner scan;
	private InputStream in;
	
	public void uploadFile(FileUploadEvent event) {

		try{

			in = event.getFile().getInputstream();

			if(in != null){
						
				columns = new ArrayList<>();
				rows = new ArrayList<>();

				scan = new Scanner(in);
				
				if(scan.hasNextLine()){
					 String csvHeader = scan.nextLine();
					 columns = Arrays.asList(csvHeader.split(","));
				}
	
				Map<String, Object> mapObject;
				while (scan.hasNextLine()) {
					
					String [] csvData = scan.nextLine().split(",");
					
					mapObject = new HashMap<>();
					for (int i = 0; i < csvData.length; i++) {
						mapObject.put(columns.get(i), csvData[i]);
					}					
					rows.add(mapObject);
				}
			}
			
			 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succesful", event.getFile().getFileName() + " is uploaded.");
		     FacesContext.getCurrentInstance().addMessage(null, message);
			
		}catch(Exception ex){
			ex.printStackTrace();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", event.getFile().getFileName() + " is uploaded.");
	        FacesContext.getCurrentInstance().addMessage(null, message);
		} finally {
			if(in != null){
				try { in.close(); } catch (IOException e) {}
			}
		}
    }

	public List<String> getColumns() {
		return columns;
	}

	public List<Map<String, Object>> getRows() {
		return rows;
	}
	
}
