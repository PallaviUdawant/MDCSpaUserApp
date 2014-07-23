package org.mdcconcepts.com.mdcspauserapp;

import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

@SuppressLint("NewApi")
public class HumanBodyFragment extends Fragment {

	private ImageView ImageView_Controller_Activity_BodyPart_Head;
	private ImageView ImageView_Controller_Activity_BodyPart_Neck;
	private ImageView ImageView_Controller_Activity_BodyPart_Shoulder;
	private ImageView ImageView_Controller_Activity_BodyPart_Arm;
	private ImageView ImageView_Controller_Activity_BodyPart_Waist;
	private ImageView ImageView_Controller_Activity_BodyPart_Back;
	private ImageView ImageView_Controller_Activity_BodyPart_Thigh;
	private ImageView ImageView_Controller_Activity_BodyPart_Calf;
	private ImageView ImageView_Controller_Activity_BodyPart_Sole;

	private ImageView ImageView_Controller_Activity_BodyPart_Head_Selection;
	private ImageView ImageView_Controller_Activity_BodyPart_Neck_Selection;
	private ImageView ImageView_Controller_Activity_BodyPart_Shoulder_Selection;
	private ImageView ImageView_Controller_Activity_BodyPart_Arm_Selection;
	private ImageView ImageView_Controller_Activity_BodyPart_Waist_Selection;
	private ImageView ImageView_Controller_Activity_BodyPart_Back_Selection;
	private ImageView ImageView_Controller_Activity_BodyPart_Thigh_Selection;
	private ImageView ImageView_Controller_Activity_BodyPart_Calf_Selection;
	private ImageView ImageView_Controller_Activity_BodyPart_Sole_Selection;

	private Button Btn_Skip_Injuries_Fragment;

	Typeface font;

	Dialog dialog;

	JSONParser jsonParser = new JSONParser();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	public HumanBodyFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.injuries, container, false);
		font = Typeface.createFromAsset(getActivity().getAssets(), Util.fontPath);
		
		Btn_Skip_Injuries_Fragment = (Button) rootView
				.findViewById(R.id.Btn_Skip_Injuries_Fragment);
		Btn_Skip_Injuries_Fragment.setTypeface(font);
		
		Btn_Skip_Injuries_Fragment
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						InjuriesActivityMain.mViewPager.setCurrentItem(1, true);
					}
				});

		ImageView_Controller_Activity_BodyPart_Head = (ImageView) rootView
				.findViewById(R.id.ImageView_Controller_Activity_BodyPart_Head);
		ImageView_Controller_Activity_BodyPart_Head_Selection = (ImageView) rootView
				.findViewById(R.id.ImageView_Controller_Activity_BodyPart_Head_Selection);

		ImageView_Controller_Activity_BodyPart_Head
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {
						if (!Util.isSelected_ImageView_Controller_Activity_BodyPart_Head) {

							ImageView_Controller_Activity_BodyPart_Head_Selection
									.setVisibility(View.VISIBLE);
							ImageView_Controller_Activity_BodyPart_Head
									.setBackgroundResource(R.drawable.head_glow);
							Util.isSelected_ImageView_Controller_Activity_BodyPart_Head = true;
							Util.PainAreas.add("1");

						} else {

							ImageView_Controller_Activity_BodyPart_Head_Selection
									.setVisibility(View.INVISIBLE);
							ImageView_Controller_Activity_BodyPart_Head
									.setBackgroundResource(R.drawable.head);
							Util.isSelected_ImageView_Controller_Activity_BodyPart_Head = false;
							Util.PainAreas.remove("1");
						}

					}
				});

		ImageView_Controller_Activity_BodyPart_Neck = (ImageView) rootView
				.findViewById(R.id.ImageView_Controller_Activity_BodyPart_Neck);
		ImageView_Controller_Activity_BodyPart_Neck_Selection = (ImageView) rootView
				.findViewById(R.id.ImageView_Controller_Activity_BodyPart_Neck_Selection);

		ImageView_Controller_Activity_BodyPart_Neck
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {
						if (!Util.isSelected_ImageView_Controller_Activity_BodyPart_Neck) {

							ImageView_Controller_Activity_BodyPart_Neck_Selection
									.setVisibility(View.VISIBLE);
							ImageView_Controller_Activity_BodyPart_Neck
									.setBackgroundResource(R.drawable.neck_glow);
							Util.isSelected_ImageView_Controller_Activity_BodyPart_Neck = true;
							Util.PainAreas.add("2");
						} else {

							ImageView_Controller_Activity_BodyPart_Neck_Selection
									.setVisibility(View.INVISIBLE);
							ImageView_Controller_Activity_BodyPart_Neck
									.setBackgroundResource(R.drawable.neck);
							Util.isSelected_ImageView_Controller_Activity_BodyPart_Neck = false;
							Util.PainAreas.remove("2");
						}

					}
				});

		ImageView_Controller_Activity_BodyPart_Shoulder = (ImageView) rootView
				.findViewById(R.id.ImageView_Controller_Activity_BodyPart_Shoulder);
		ImageView_Controller_Activity_BodyPart_Shoulder_Selection = (ImageView) rootView
				.findViewById(R.id.ImageView_Controller_Activity_BodyPart_Shoulder_Selection);

		ImageView_Controller_Activity_BodyPart_Shoulder
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {
						if (!Util.isSelected_ImageView_Controller_Activity_BodyPart_Shoulder) {

							ImageView_Controller_Activity_BodyPart_Shoulder_Selection
									.setVisibility(View.VISIBLE);
							ImageView_Controller_Activity_BodyPart_Shoulder
									.setBackgroundResource(R.drawable.shoulder_glow);
							Util.isSelected_ImageView_Controller_Activity_BodyPart_Shoulder = true;
							Util.PainAreas.add("3");
							//
						} else {

							ImageView_Controller_Activity_BodyPart_Shoulder_Selection
									.setVisibility(View.INVISIBLE);
							ImageView_Controller_Activity_BodyPart_Shoulder
									.setBackgroundResource(R.drawable.shoulder);
							Util.isSelected_ImageView_Controller_Activity_BodyPart_Shoulder = false;
							Util.PainAreas.remove("3");
						}

					}
				});
		ImageView_Controller_Activity_BodyPart_Arm = (ImageView) rootView
				.findViewById(R.id.ImageView_Controller_Activity_BodyPart_arm);
		ImageView_Controller_Activity_BodyPart_Arm_Selection = (ImageView) rootView
				.findViewById(R.id.ImageView_Controller_Activity_BodyPart_Arm_Selection);

		ImageView_Controller_Activity_BodyPart_Arm
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {
						if (!Util.isSelected_ImageView_Controller_Activity_BodyPart_Arm) {

							ImageView_Controller_Activity_BodyPart_Arm_Selection
									.setVisibility(View.VISIBLE);
							ImageView_Controller_Activity_BodyPart_Arm
									.setBackgroundResource(R.drawable.arm_glow);
							Util.isSelected_ImageView_Controller_Activity_BodyPart_Arm = true;
							Util.PainAreas.add("4");
						} else {

							ImageView_Controller_Activity_BodyPart_Arm_Selection
									.setVisibility(View.INVISIBLE);
							ImageView_Controller_Activity_BodyPart_Arm
									.setBackgroundResource(R.drawable.arm);
							Util.isSelected_ImageView_Controller_Activity_BodyPart_Arm = false;
							Util.PainAreas.remove("4");
						}

					}
				});

		ImageView_Controller_Activity_BodyPart_Waist = (ImageView) rootView
				.findViewById(R.id.ImageView_Controller_Activity_BodyPart_waist);
		ImageView_Controller_Activity_BodyPart_Waist_Selection = (ImageView) rootView
				.findViewById(R.id.ImageView_Controller_Activity_BodyPart_Waist_Selection);

		ImageView_Controller_Activity_BodyPart_Waist
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {
						if (!Util.isSelected_ImageView_Controller_Activity_BodyPart_Waist) {

							ImageView_Controller_Activity_BodyPart_Waist_Selection
									.setVisibility(View.VISIBLE);
							ImageView_Controller_Activity_BodyPart_Waist
									.setBackgroundResource(R.drawable.waist_glow);
							Util.isSelected_ImageView_Controller_Activity_BodyPart_Waist = true;
							Util.PainAreas.add("5");
						} else {

							ImageView_Controller_Activity_BodyPart_Waist_Selection
									.setVisibility(View.INVISIBLE);
							ImageView_Controller_Activity_BodyPart_Waist
									.setBackgroundResource(R.drawable.waist);
							Util.isSelected_ImageView_Controller_Activity_BodyPart_Waist = false;
							Util.PainAreas.remove("5");
						}

					}
				});
		ImageView_Controller_Activity_BodyPart_Back = (ImageView) rootView
				.findViewById(R.id.ImageView_Controller_Activity_BodyPart_back);
		ImageView_Controller_Activity_BodyPart_Back_Selection = (ImageView) rootView
				.findViewById(R.id.ImageView_Controller_Activity_BodyPart_Back_Selection);

		ImageView_Controller_Activity_BodyPart_Back
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {
						if (!Util.isSelected_ImageView_Controller_Activity_BodyPart_Back) {

							ImageView_Controller_Activity_BodyPart_Back_Selection
									.setVisibility(View.VISIBLE);
							ImageView_Controller_Activity_BodyPart_Back
									.setBackgroundResource(R.drawable.back_glow);
							Util.isSelected_ImageView_Controller_Activity_BodyPart_Back = true;
							Util.PainAreas.add("6");
						} else {

							ImageView_Controller_Activity_BodyPart_Back_Selection
									.setVisibility(View.INVISIBLE);
							ImageView_Controller_Activity_BodyPart_Back
									.setBackgroundResource(R.drawable.back);
							Util.isSelected_ImageView_Controller_Activity_BodyPart_Back = false;
							Util.PainAreas.remove("6");
						}

					}
				});

		ImageView_Controller_Activity_BodyPart_Thigh = (ImageView) rootView
				.findViewById(R.id.ImageView_Controller_Activity_BodyPart_thigh);
		ImageView_Controller_Activity_BodyPart_Thigh_Selection = (ImageView) rootView
				.findViewById(R.id.ImageView_Controller_Activity_BodyPart_Thigh_Selection);

		ImageView_Controller_Activity_BodyPart_Thigh
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {
						if (!Util.isSelected_ImageView_Controller_Activity_BodyPart_Thigh) {

							ImageView_Controller_Activity_BodyPart_Thigh_Selection
									.setVisibility(View.VISIBLE);
							ImageView_Controller_Activity_BodyPart_Thigh
									.setBackgroundResource(R.drawable.thigh_glow);
							Util.isSelected_ImageView_Controller_Activity_BodyPart_Thigh = true;
							Util.PainAreas.add("7");
						} else {

							ImageView_Controller_Activity_BodyPart_Thigh_Selection
									.setVisibility(View.INVISIBLE);
							ImageView_Controller_Activity_BodyPart_Thigh
									.setBackgroundResource(R.drawable.thigh);
							Util.isSelected_ImageView_Controller_Activity_BodyPart_Thigh = false;
							Util.PainAreas.remove("7");
						}

					}
				});

		ImageView_Controller_Activity_BodyPart_Calf = (ImageView) rootView
				.findViewById(R.id.ImageView_Controller_Activity_BodyPart_calf);
		ImageView_Controller_Activity_BodyPart_Calf_Selection = (ImageView) rootView
				.findViewById(R.id.ImageView_Controller_Activity_BodyPart_Calf_Selection);

		ImageView_Controller_Activity_BodyPart_Calf
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {
						if (!Util.isSelected_ImageView_Controller_Activity_BodyPart_Calf) {

							ImageView_Controller_Activity_BodyPart_Calf_Selection
									.setVisibility(View.VISIBLE);
							ImageView_Controller_Activity_BodyPart_Calf
									.setBackgroundResource(R.drawable.calf_glow);
							Util.isSelected_ImageView_Controller_Activity_BodyPart_Calf = true;
							Util.PainAreas.add("8");
						} else {

							ImageView_Controller_Activity_BodyPart_Calf_Selection
									.setVisibility(View.INVISIBLE);
							ImageView_Controller_Activity_BodyPart_Calf
									.setBackgroundResource(R.drawable.calf);
							Util.isSelected_ImageView_Controller_Activity_BodyPart_Calf = false;
							Util.PainAreas.remove("8");
						}

					}
				});
		ImageView_Controller_Activity_BodyPart_Sole = (ImageView) rootView
				.findViewById(R.id.ImageView_Controller_Activity_BodyPart_sole);
		ImageView_Controller_Activity_BodyPart_Sole_Selection = (ImageView) rootView
				.findViewById(R.id.ImageView_Controller_Activity_BodyPart_Sole_Selection);

		ImageView_Controller_Activity_BodyPart_Sole
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {
						if (!Util.isSelected_ImageView_Controller_Activity_BodyPart_Sole) {

							ImageView_Controller_Activity_BodyPart_Sole_Selection
									.setVisibility(View.VISIBLE);
							ImageView_Controller_Activity_BodyPart_Sole
									.setBackgroundResource(R.drawable.sole_glow);
							Util.isSelected_ImageView_Controller_Activity_BodyPart_Sole = true;
							Util.PainAreas.add("9");
							//
						} else {

							ImageView_Controller_Activity_BodyPart_Sole_Selection
									.setVisibility(View.INVISIBLE);
							ImageView_Controller_Activity_BodyPart_Sole
									.setBackgroundResource(R.drawable.sole);
							Util.isSelected_ImageView_Controller_Activity_BodyPart_Sole = false;
							Util.PainAreas.remove("9");
						}
					}
				});

		if (Util.isSelected_ImageView_Controller_Activity_BodyPart_Head) {
			ImageView_Controller_Activity_BodyPart_Head_Selection
					.setVisibility(View.VISIBLE);
			ImageView_Controller_Activity_BodyPart_Head
					.setBackgroundResource(R.drawable.head_glow);
		}

		if (Util.isSelected_ImageView_Controller_Activity_BodyPart_Neck) {
			ImageView_Controller_Activity_BodyPart_Neck_Selection
					.setVisibility(View.VISIBLE);
			ImageView_Controller_Activity_BodyPart_Neck
					.setBackgroundResource(R.drawable.neck_glow);
		}

		if (Util.isSelected_ImageView_Controller_Activity_BodyPart_Shoulder) {
			ImageView_Controller_Activity_BodyPart_Shoulder_Selection
					.setVisibility(View.VISIBLE);
			ImageView_Controller_Activity_BodyPart_Shoulder
					.setBackgroundResource(R.drawable.shoulder_glow);
		}

		if (Util.isSelected_ImageView_Controller_Activity_BodyPart_Arm) {
			ImageView_Controller_Activity_BodyPart_Arm_Selection
					.setVisibility(View.VISIBLE);
			ImageView_Controller_Activity_BodyPart_Arm
					.setBackgroundResource(R.drawable.arm_glow);
		}
		if (Util.isSelected_ImageView_Controller_Activity_BodyPart_Waist) {
			ImageView_Controller_Activity_BodyPart_Waist_Selection
					.setVisibility(View.VISIBLE);
			ImageView_Controller_Activity_BodyPart_Waist
					.setBackgroundResource(R.drawable.waist_glow);
		}
		if (Util.isSelected_ImageView_Controller_Activity_BodyPart_Back) {
			ImageView_Controller_Activity_BodyPart_Back_Selection
					.setVisibility(View.VISIBLE);
			ImageView_Controller_Activity_BodyPart_Back
					.setBackgroundResource(R.drawable.back_glow);
		}

		if (Util.isSelected_ImageView_Controller_Activity_BodyPart_Thigh) {
			ImageView_Controller_Activity_BodyPart_Thigh_Selection
					.setVisibility(View.VISIBLE);
			ImageView_Controller_Activity_BodyPart_Thigh
					.setBackgroundResource(R.drawable.thigh_glow);
		}

		if (Util.isSelected_ImageView_Controller_Activity_BodyPart_Calf) {
			ImageView_Controller_Activity_BodyPart_Calf_Selection
					.setVisibility(View.VISIBLE);
			ImageView_Controller_Activity_BodyPart_Calf
					.setBackgroundResource(R.drawable.calf_glow);
		}

		if (Util.isSelected_ImageView_Controller_Activity_BodyPart_Sole) {
			ImageView_Controller_Activity_BodyPart_Sole_Selection
					.setVisibility(View.VISIBLE);
			ImageView_Controller_Activity_BodyPart_Sole
					.setBackgroundResource(R.drawable.sole_glow);
		}
		font = Typeface.createFromAsset(getActivity().getAssets(),
				Util.fontPath);

		return rootView;
	}

	
}
