/**
 * @file org/pepit/m/francais/lelales/Exercise.java
 * 
 * PepitModel: an educational software
 * This file is a part of the PepitModel environment
 * http://pepit.be
 *
 * Copyright (C) 2012-2013 Pepit Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.pepit.m.francais.lelales;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Exercise extends Activity {
	private static final String LE = "le";
	private static final String LA = "la";
	private static final String LES = "es";
	
	private Button btnLe, btnLa, btnLes;
	private TextView status;
	private ImageView imgView;
	
	private int startImg;
	private int module;
	private ArrayList<Integer> listImgRes;
	private ArrayList<String> listImgUri;
	
	private int currentQuestion;
	private String currentAnswer;
	private int score;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.m_francais_lelales_practice);
		
		this.setContentView(createLayout());
		
//		get data
		Bundle bundle = this.getIntent().getExtras();
        if (bundle != null && bundle.containsKey("module") && bundle.containsKey("start")){
        	startImg = this.getIntent().getIntExtra("start",1);
        	module = this.getIntent().getIntExtra("module", 1);
        }else{
        	startImg = 1;
        	module = 1;
        }
        
//		init
		listImgRes = new ArrayList<Integer>();
		listImgUri = new ArrayList<String>();
        currentQuestion = -1;
        currentAnswer = "";
        score = 0;
        
        // Cette partie ne marche plus, faire nouvelle méthode
//     	get imgs
//        for(int i=startImg; i<=(startImg+4);i++){
//        	String uriStart = "drawable/m_francais_lelales_"+i+"_";
//        	
//        	String uri = uriStart+"le";
//        	int imageRessource = getResources().getIdentifier(uri, null, getPackageName());
//            
//        	if(imageRessource == 0){
//        		uri = uriStart+"la";
//        		imageRessource = getResources().getIdentifier(uri, null, getPackageName());
//        	}
//        	if(imageRessource == 0){
//        		uri = uriStart+"les";
//        		imageRessource = getResources().getIdentifier(uri, null, getPackageName());
//        	}
        	
//        	listImgUri.add(uri);
//        	listImgRes.add(imageRessource);
//        }
        
//     	btns
        btnLe.setOnClickListener(new View.OnClickListener() {	
			public void onClick(View v) {
				checkAnswer(LE);
			}
		});
        btnLa.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		checkAnswer(LA);
			}
		});
        btnLes.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		checkAnswer(LES);
			}
		});
       
//     	switch
        nextQuestion();
        updateStatus();
	}
	
	/**
	 * switch next question
	 */
	private void nextQuestion(){
		currentQuestion++;
		
//		get answer
		String uri = listImgUri.get(currentQuestion);
		currentAnswer = uri.substring(uri.length()-2,uri.length());
		
//		load img
		imgView.setImageDrawable(getResources().getDrawable(listImgRes.get(currentQuestion)));
	}
	
	/**
	 * Check answer
	 * @param answer
	 * @return
	 */
	private boolean isGoodAnswer(String answer){		
		if(answer.compareTo(currentAnswer)==0)
			return true;
		else
			return false;
	}
	
	/**
	 * Check answer
	 * @param answer
	 */
	private void checkAnswer(String answer){
		if(isGoodAnswer(answer)){ // good
			score++;
			updateStatus();
			if(currentQuestion == 4){ // it's over
				Toast.makeText(getApplicationContext(), "Vous avez fini la série de question", Toast.LENGTH_SHORT).show();
				
				btnLe.setEnabled(false);
				btnLa.setEnabled(false);
				btnLes.setEnabled(false);
			}else
				nextQuestion();
		}else // wrong
			Toast.makeText(getApplicationContext(), "Ce n'est pas la bonne réponse", Toast.LENGTH_SHORT).show();
	}
	/**
	 * Update status bar
	 */
	private void updateStatus(){
		String text = "M - Français - Exercice "+module; // TODO a modif 
		text += " [score : "+score+"]";
		status.setText(text);
	}
	
//	layout
	@SuppressLint("NewApi")
	private LinearLayout createLayout(){
		LinearLayout layoutGlobal = new LinearLayout(getApplicationContext());
		layoutGlobal.setOrientation(LinearLayout.VERTICAL);
		layoutGlobal.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		
		LinearLayout layoutGame = new LinearLayout(getApplicationContext());
		layoutGame.setOrientation(LinearLayout.HORIZONTAL);
		layoutGame.setWeightSum(15);
		layoutGame.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT));
		
		LinearLayout layoutButtons = new LinearLayout(getApplicationContext());
		layoutButtons.setOrientation(LinearLayout.VERTICAL);
		layoutButtons.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT));
		
//		Button btnLe, btnLa, btnLes;
		btnLe = createButton("Le");
		btnLa = createButton("La");
		btnLes = createButton("Les");
		
		LinearLayout layoutImage = new LinearLayout(getApplicationContext());
		layoutImage.setOrientation(LinearLayout.VERTICAL);
		layoutImage.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
		
		imgView = new ImageView(getApplicationContext());
		imgView.setContentDescription("Game picture");
		imgView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		
//		appellé à disparaitre avec le système de plugin
		LinearLayout layoutFooter = new LinearLayout(getApplicationContext());
		layoutFooter.setOrientation(LinearLayout.HORIZONTAL);
		layoutFooter.setWeightSum(1);
		layoutFooter.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		
		status = new TextView(getApplicationContext());
		status.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		
		// relié le petit monde
		layoutButtons.addView(btnLe);
		layoutButtons.addView(btnLa);
		layoutButtons.addView(btnLes);
		
		layoutImage.addView(imgView);
		
		layoutGame.addView(layoutButtons);
		layoutGame.addView(layoutImage);
		
		layoutFooter.addView(status);
		
		layoutGlobal.addView(layoutGame);
		layoutGlobal.addView(layoutFooter);
		
		return layoutGlobal;
	}
	
	@SuppressLint("NewApi")
	private Button createButton(String type){
		Button btn = new Button(getApplicationContext());
		btn.setText(type);
//		No style
		LayoutParams paramsBtn = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		btn.setLayoutParams(paramsBtn);
		
		return btn;
	}
}
