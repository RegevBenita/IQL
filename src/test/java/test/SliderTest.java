package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import ast.Id;
import ast.components.CSlider;
import ast.constraints.Constraint;
import ast.constraints.DisplayId;
import fields.JPanelWithValue;

public class SliderTest {
	
	@Test public void slider1() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			height 'Height:' Slider['0, 5']('1')
			{majorTicks=3 minorTicks=1 inline}
			""","""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Slider [name=height, prompt=Height:, \
			minVal=0, maxVal=5, defVal=1, \
			constraints=[MajorTicksCon[value=3], \
			MinorTicksCon[value=1], Inline]]]]\
			""");
	}
	
	@Test public void slider2() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			height 'Height:' Slider['0, 5']('1')
			""","""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Slider [name=height, prompt=Height:, \
			minVal=0, maxVal=5, defVal=1, \
			constraints=[]]]]\
			""");
	}
	
	@Test public void slider3() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			height 'Height:' Slider['2, 5']
			""","""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Slider [name=height, prompt=Height:, \
			minVal=2, maxVal=5, defVal=2, \
			constraints=[]]]]\
			""");
	}
	
	@Test public void slider4() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			height 'Height:' Slider['2, 5']{block}
			""","""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Slider [name=height, prompt=Height:, \
			minVal=2, maxVal=5, defVal=2, \
			constraints=[Block]]]]\
			""");
	}
	
	@Test public void slider5() {
		TestHelper.numberException("""
			'Single Dialog' Single('single my description')
			height 'Height:' Slider['0.1, 5']('1')
			{majorTicks=3 minorTicks=1 inline}
			""",
			"Invalid Slider default values");
	}
	
	@Test public void slider6() {
		TestHelper.numberException("""
			'Single Dialog' Single('single my description')
			height 'Height:' Slider['1, 5']('6')
			{majorTicks=3 minorTicks=1 inline}
			""",
			"Default value must be between or equal to the min and max values");
	}
	
	@Test public void slider7() {
		TestHelper.arrgumentException("""
			'Single Dialog' Single('single my description')
			height 'Height:' Slider['hi there']('6')
			{majorTicks=3 minorTicks=1 inline}
			""",
			"hi there must contain 2 numbers seperated by comma");
	}
	
	@Test public void slider8() {
		TestHelper.checkParseError("""
			'Single Dialog' Single('single my description')
			height 'Height:' Slider['1, 5'] ('1')
			{majorTicks=3 minorTicks=1.3 inline}
			""");
	}
	
	@Test public void testSliderBlockDisplay() {
		  var frame=new JFrame();
		  Constraint constraint = DisplayId.from(Id.String, "block");
		  List<Constraint> constraints = List.of(constraint);
		  var cs=new CSlider("name","title",2,9,5,constraints);
		  JPanelWithValue slider=cs.make();
		  assertEquals("5", slider.getValue());
		  slider.setValueOrDefault("3", false);
		  assertEquals("3", slider.getValue());
		  slider.setValueOrDefault("3", true);
		  assertEquals("5", slider.getValue());
		  TestHelper.withGui(frame, slider, false);
	  }
	
	@Test public void testSliderinlineDisplay() {
		  var frame=new JFrame();
		  Constraint constraint = DisplayId.from(Id.String, "inline");
		  List<Constraint> constraints = List.of(constraint);
		  var cs=new CSlider("name","title",2,9,5,constraints);
		  JPanelWithValue slider=cs.make();
		  assertEquals("5", slider.getValue());
		  slider.setValueOrDefault("3", false);
		  assertEquals("3", slider.getValue());
		  slider.setValueOrDefault("3", true);
		  assertEquals("5", slider.getValue());
		  TestHelper.withGui(frame, slider, false);
	  }
	
}