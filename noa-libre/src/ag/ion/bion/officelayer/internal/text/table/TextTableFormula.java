/****************************************************************************
 * ubion.ORS - The Open Report Suite                                        *
 *                                                                          *
 * ------------------------------------------------------------------------ *
 *                                                                          *
 * Subproject: NOA (Nice Office Access)                                     *
 *                                                                          *
 *                                                                          *
 * The Contents of this file are made available subject to                  *
 * the terms of GNU Lesser General Public License Version 2.1.              *
 *                                                                          * 
 * GNU Lesser General Public License Version 2.1                            *
 * ======================================================================== *
 * Copyright 2003-2005 by IOn AG                                            *
 *                                                                          *
 * This library is free software; you can redistribute it and/or            *
 * modify it under the terms of the GNU Lesser General Public               *
 * License version 2.1, as published by the Free Software Foundation.       *
 *                                                                          *
 * This library is distributed in the hope that it will be useful,          *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of           *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU        *
 * Lesser General Public License for more details.                          *
 *                                                                          *
 * You should have received a copy of the GNU Lesser General Public         *
 * License along with this library; if not, write to the Free Software      *
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston,                    *
 * MA  02111-1307  USA                                                      *
 *                                                                          *
 * Contact us:                                                              *
 *  http://www.ion.ag                                                       *
 *  info@ion.ag                                                             *
 *                                                                          *
 ****************************************************************************/
 
/*
 * Last changes made by $Author: andreas $, $Date: 2006-10-04 14:14:28 +0200 (Mi, 04 Okt 2006) $
 */
package ag.ion.bion.officelayer.internal.text.table;

import ag.ion.bion.officelayer.text.TextException;

import ag.ion.bion.officelayer.text.table.IArgument;
import ag.ion.bion.officelayer.text.table.IFormula;
import ag.ion.bion.officelayer.text.table.ITextTableCellReferencesService;
import ag.ion.bion.officelayer.text.table.TextTableCellNameHelper;

import java.util.ArrayList;

/**
 * Class for parsing a formula.
 * 
 * @author Miriam Sutter
 * @version $Revision: 10398 $
 */
public class TextTableFormula implements IFormula {

	//Constants
	public final static String 	PI								=	"PI";
	public final static String	E									=	"E";
	
	//Operator
	public static final String	PLUS							=	"+";
	public static final String	MINUS							=	"-";
	public static final String	DIVIDE						=	"/";
	public static final String 	MULTIPLY					=	"*";
	
	public static final String 	ROUND							=	"round";
	public static final String	PERCENT						=	"phd";
	public static final String	SQRT							=	"sqrt";
	public static final String	POW								=	"pow";
	public static final String	MEAN							=	"mean";
	
	public static final	String[] operators = new String[] {
																									PLUS, 
																									MINUS, 
																									DIVIDE, 
																									MULTIPLY,
																									ROUND,
																									PERCENT,
																									SQRT,
																									POW,
																									MEAN
																							};
	
	private final String	EQUAL							= "eq";
	private final String	NOTEQUAL					=	"neq";
	private final String 	LOWEREQUAL				= "leq";
	private final String 	GREATEREQUAL			=	"geq";
	private final String	LOWER							=	"l";
	private final String	GREATER						=	"g";
	private final	String	OR								=	"or";
	private final String 	XOR								=	"xor";
	private final String 	AND								=	"and";
	private final String 	NOT								=	"not";
	
	//Functions
	private final String 	MIN								=	"min";
	private final String	MAX								=	"max";
	private final String	SIN								=	"sin";
	private final String 	COS								=	"cos";
	private final String 	TAN								=	"tan";
	private final String	ASIN							=	"asin";
	private final String	ACOS							=	"acos";
	private final String	ATAN							=	"atan";
		
	private TextTableFormulaExpression textTableFormulaExpression = null;
	
	private Arguments arguments	= null; 
  
  private ITextTableCellReferencesService textTableCellReferencesService = null;
  
	private ArrayList elements = new ArrayList();  
	
  //----------------------------------------------------------------------------
	/**
	 * Constructs new Formula on the basis of the submitted expression.
	 * 
	 * @param formulaExpression formula expression to be used
	 * 
	 * @throws IllegalArgumentException if the submitted formula is not valid
	 * 
	 * @author Miriam Sutter
	 */
	public TextTableFormula(TextTableFormulaExpression textTableFormulaExpression) throws IllegalArgumentException {
		if(textTableFormulaExpression == null)
      throw new IllegalArgumentException("Submitted formula is not valid");
		this.textTableFormulaExpression = textTableFormulaExpression;
  }  
  //----------------------------------------------------------------------------
	/**
	 * Returns the arguments of the formula.
	 * 
	 * @return arguments of the formula
	 * 
	 * @author Miriam Sutter
	 */
	public IArgument[] getArguments() {
    if(arguments == null) {
      arguments = new Arguments();
      parse();
    }
		return arguments.getArguments();
	}
  //----------------------------------------------------------------------------
	/**
	 * Calculates the formula.
	 * 
	 * @throws TextException if the formula can not be calculated
	 * 
	 * @author Miriam Sutter
	 */
	public double calcFormula() throws TextException {
		try {
      if(arguments == null) {
        arguments = new Arguments();
        parse();
      }
      ITextTableFormulaElement[] formulaElements = new ITextTableFormulaElement[elements.size()];
      formulaElements = (ITextTableFormulaElement[])elements.toArray(formulaElements);
      
		  return CalculateFormula.calculate(formulaElements,arguments, textTableFormulaExpression.getExpression());
	  }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns text table cell references service.
   * 
   * @return text table cell references service
   * 
   * @author Andreas Bröker
   */
  public ITextTableCellReferencesService getCellReferencesService() {
    if(textTableCellReferencesService == null) {      
      textTableCellReferencesService = new TextTableCellReferencesService(textTableFormulaExpression);
    }
    return textTableCellReferencesService;
  }    
  //----------------------------------------------------------------------------
  /**
   * Returns formula expression.
   * 
   * @return formula expression
   * 
   * @author Andreas Bröker
   */
  public String getExpression() {     
    return textTableFormulaExpression.getExpression();
  }
	//----------------------------------------------------------------------------
  /**
   * Parses the formula.
   * 
   * @author Miriam Sutter
   */
  private void parse() {
  	getMaxMin(true,textTableFormulaExpression.getExpression(),0);
    getMaxMin(false,textTableFormulaExpression.getExpression(),0);
  	findArguments(textTableFormulaExpression.getExpression());
    replacePipes();
    replaceMean();
    analyseBrackets(textTableFormulaExpression.getExpression(), elements);
  }
  //----------------------------------------------------------------------------
  /**
   * Finds the arguments in the formula.
   * 
   * @param formulaArgument the formula to find the arguments
   * 
   * @author Miriam Sutter
   */
  private void findArguments(String formulaArgument) {
    int posStart = formulaArgument.indexOf("<");
    int posEnd = formulaArgument.indexOf(">");
    if(posStart != -1 && posEnd != -1) {
      String argumentBez = formulaArgument.substring(posStart,posEnd+1);
      if(argumentBez.indexOf(":")!=-1) {
        String[] help = argumentBez.substring(1,argumentBez.length()-1).split(":");
        String helpString = getRangeArguments(help[0],help[1],arguments);
        textTableFormulaExpression.setExpression(textTableFormulaExpression.getExpression().replaceFirst(argumentBez,helpString));
      }
      else if(argumentBez.indexOf("|") != -1) {
        String pipe = replacePipe(argumentBez);
        int index = textTableFormulaExpression.getExpression().indexOf(argumentBez);
        textTableFormulaExpression.setExpression(textTableFormulaExpression.getExpression().substring(0,index) + "(" + pipe + ")" + textTableFormulaExpression.getExpression().substring(index+argumentBez.length()));
      }
      else {
        Argument argument = new Argument(argumentBez);
        argument.setCellReference(true);
        if(arguments.getArgument(argument.getName()) == null) {
        	arguments.addArgument(argument);
        }
      }
      String helpFormula = formulaArgument.substring(posEnd+1);
      findArguments(helpFormula);
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Gets the Arguments of a range.
   * 
   * @param argumentsArray the begin and the end of a range.
   * 
   * @return the arguments of the range
   * 
   * @author Miriam Sutter
   */
  private String getRangeArguments(String firstArguments, String secondArgument, Arguments arguments) {
    StringBuffer argumentStringBuffer = new StringBuffer();
    String startString    = getArgumentCellName(firstArguments);
    String endString      = getArgumentCellName(secondArgument);
    String helpString = startString;
    int rowStartNumber = getRowNumber(firstArguments);
    int rowEndNumber = getRowNumber(secondArgument);
    int rowHelpNumber = rowStartNumber;
    
    Argument argument = new Argument("<" +startString + rowStartNumber+ ">");
    argument.setCellReference(true);
    arguments.addArgument(argument);
    argumentStringBuffer.append("(" + argument.getName());
    boolean first = true;
    boolean finish = false;
    do {
      if(rowHelpNumber != rowStartNumber || !startString.equals(endString) && !first) {
        argument = new Argument("<" + helpString + rowHelpNumber + ">");
        argument.setCellReference(true);
        if(arguments.getArgument(argument.getName()) == null) {
        	arguments.addArgument(argument);
        }
        argumentStringBuffer.append(" + " + argument.getName());
      }
      first = false;
      if(helpString.equals(endString)) {
        helpString = startString;
        if(rowHelpNumber < rowEndNumber) {
          rowHelpNumber++;
        }
        else {
          finish = true;
        }
      }
      else {
        if(!startString.equals(endString)) {
          helpString = TextTableCellNameHelper.getNextColumnName(helpString);
        }
      }
    }
    while(!finish);
    argumentStringBuffer.append(")");
    return argumentStringBuffer.toString();
  }
  //----------------------------------------------------------------------------
  /**
   * Gets the name of the cell.
   * 
   * @param argument the cell
   * 
   * @return the cell name
   * 
   * @author Miriam Sutter
   */
  private String getArgumentCellName(String argument) {
    int pos = -1;
    for(int i = 0; i < argument.length(); i++) {
      if(!Character.isDigit(argument.charAt(i))) {
        pos = i;
      }
      else {
        break;
      }
    }
    return argument.substring(0,pos+1);
  }
  //----------------------------------------------------------------------------
  /**
   * Gets the row number
   * 
   * @param argument the cell
   * 
   * @return the row number
   * 
   * @author Miriam Sutter
   */
  private int getRowNumber(String argument) {
    int pos = -1;
    for(int i = 0; i < argument.length(); i++) {
      if(Character.isDigit(argument.charAt(i))) {
        pos = i;
      }
    };
    return new Integer(argument.substring(pos)).intValue();
  }
  //----------------------------------------------------------------------------
  /**
   * Searchs and analyses the brackets.
   * 
   * @param formulaBracket the formula to analyse
   * @param bracketElements the bracket elements list
   * 
   * @author Miriam Sutter
   */
  private void analyseBrackets(String formulaBracket, ArrayList bracketElements) {
    int startPos = formulaBracket.indexOf('(');
    int endPos = -1;
    
    if(startPos == -1) return;
    
    int countBrackets = 1;
    for(int i = startPos+1; i < formulaBracket.length(); i++) {
      countBrackets = getEndPos(formulaBracket,countBrackets,i,'(',')');
      if(countBrackets == 0) {
        endPos =  i;
        break;
      }
    }
    
    if(endPos != -1) {
      BracketElement bracketElement = new BracketElement(formulaBracket.substring(startPos, endPos+1));
      appendArguments(bracketElement.getFormula(),bracketElement);
      ArrayList internalBracketElements = new ArrayList();
      analyseBrackets(formulaBracket.substring(startPos+1, endPos), internalBracketElements);
      BracketElement[] elements = new BracketElement[internalBracketElements.size()];
      bracketElement.setBracketElements((BracketElement[])internalBracketElements.toArray(elements));
      bracketElements.add(bracketElement);
      analyseBrackets(formulaBracket.substring(endPos), bracketElements);
    }
  }  
  //----------------------------------------------------------------------------
	/**
	 * Appends an argument.
	 * 
	 * @param formulaElement the formula element
	 */
	private void appendArguments(String formula,ITextTableFormulaElement formulaElement) {
		int startPos = formula.indexOf("<");
		int endPos = formula.indexOf(">");
		if(startPos != -1 && endPos != -1) {
			formulaElement.addArgument(arguments.getArgument(formula.substring(startPos,endPos+1)));
			appendArguments(formula.substring(endPos + 1),formulaElement);
		}
	}
  //----------------------------------------------------------------------------
	/**
	 * Replaces the pipe.
	 * 
	 * @param argumentBez the formula to replace
	 * 
	 * @return the new formula
	 * 
	 * @author Miriam Sutter
	 */
	private String replacePipe(String argumentBez) {
		String help1 = argumentBez.substring(0,argumentBez.indexOf("|")) + ">";
		String help2 = "<" + argumentBez.substring(argumentBez.indexOf("|")+1);
		String returnString = "";
		
		Argument argument = new Argument(help1);
		argument.setCellReference(true);
		if(arguments.getArgument(argument.getName()) == null) {
    	arguments.addArgument(argument);
    }
		returnString = help1;
		
		if(help2.indexOf("|")!= -1) {
			returnString = returnString +  "+" + replacePipe(help2);
		}
		else {
			argument = new Argument(help2);
			argument.setCellReference(true);
			if(arguments.getArgument(argument.getName()) == null) {
      	arguments.addArgument(argument);
      }
			returnString = returnString + "+" + help2;
		}
		return returnString;
	}
  //----------------------------------------------------------------------------
	/**
	 * Replaces the pipes.
	 *
	 * @author Miriam Sutter
	 */
	private void replacePipes() {
		String formula = textTableFormulaExpression.getExpression();
		int index = formula.indexOf('|');
		boolean found = false;
		if(index !=-1) {
			do {
				formula = formula.substring(0, index) + "+" + formula.substring(index+1);
				index = formula.indexOf('|');
				if(index == -1) {
					found = true;
				}
			}while(!found);
			textTableFormulaExpression.setExpression(formula);
		}
	}
  //----------------------------------------------------------------------------
	/**
	 * Finds the end position of a bracket.
	 * 
	 * @param formulaBracket the string to find the positions
	 * @param countBrackets the number of brackets
	 * @param i the index of the bracket
	 * 
	 * @return the end position
	 * 
	 * @author Miriam Sutter
	 */
	private int getEndPos(String formulaBracket, int countBrackets, int i,char firstBracket,char lastBracket) {
		if(formulaBracket.charAt(i) == firstBracket) {
			countBrackets++;
		}
		else if(formulaBracket.charAt(i) == lastBracket) {
			countBrackets--;
		}
		return countBrackets;
	}
  //----------------------------------------------------------------------------
	/**
	 * Replaces the mean function.
	 * 
	 * @author Miriam Sutter
	 */
	private void replaceMean() {
		String formula = textTableFormulaExpression.getExpression();
		int index = formula.indexOf(TextTableFormula.MEAN);
		String help = formula.substring(index + TextTableFormula.MEAN.length());
		int endPos = formula.length();
		if(index != -1) {
			if(index > 0) {
				if(formula.charAt(index-1) == '(') {
					int countBrackets = 1;
					for(int i = index; i < formula.length(); i++) {
			      countBrackets = getEndPos(formula,countBrackets,i,'(',')');
			      if(countBrackets == 0) {
			        endPos =  i;
			        break;
			      }
			    }
					help = formula.substring(index + TextTableFormula.MEAN.length(),endPos);
				}
			}
			MeanElement meanElement = new MeanElement(help);
			formula = formula.substring(0, index) + meanElement.replace() + formula.substring(endPos) ; 
		}
		textTableFormulaExpression.setExpression(formula);
	}
  //----------------------------------------------------------------------------
	/**
	 * Gets the maximum and minimum value
	 * 
	 * @param max true, if it is the maximum
	 * 
	 * @author Miriam Sutter
	 */
	private void getMaxMin(boolean maximum, String formula, int formulaIndex) {
		String maxMin = MAX;
		boolean found = false;
		int helpIndex = 0;
		Arguments maxArguments = new Arguments();
		if(!maximum) {
			maxMin = MIN;
		}
		int index = formula.indexOf(maxMin);
		if(index != -1) {
			MaxMinElement maxMinElement = new MaxMinElement(maxMin + (index+formulaIndex),maximum);
			String help = formula.substring(index + maxMin.length());
			int oldIndex = 0;
			for(int i = 0; i < help.length(); i++) {
				if(help.charAt(i) == '|') {
					helpIndex = maxMin.length() + (help.lastIndexOf("|") + help.substring(help.lastIndexOf("|")).indexOf(">")) + 1;
					found = true;
					String helpString = help.substring(oldIndex,i).trim();
					if(helpString.indexOf("<") != -1) {
						helpString = helpString.substring(helpString.indexOf("<"), helpString.indexOf(">")+1).trim();
						if(helpString.indexOf(":")!=-1) {
			        String[] helpArray = helpString.substring(1,helpString.length()-1).split(":");
			        getRangeArguments(helpArray[0],helpArray[1],maxArguments);
			        Argument[] argumentsArray = (Argument[])maxArguments.getArguments();
			        for(int j = 0, m = argumentsArray.length; j < m; j++) {
			        	if(arguments.getArgument(argumentsArray[j].getName()) == null) {
			        		arguments.addArgument(argumentsArray[j]);
				        }
			        	else {
			        		maxArguments.addArgument(arguments.getArgument(argumentsArray[j].getName()));
			        	}
			        }
				    }
			      else {
			      	Argument argument = new Argument(helpString);
			        argument.setCellReference(true);
			        maxArguments.addArgument(argument);
			        if(arguments.getArgument(argument.getName()) == null) {
			        	arguments.addArgument(argument);
			        }
			        else {
			        	maxArguments.addArgument(arguments.getArgument(argument.getName()));
			        }
			      }
					}
					else {
						Argument argument = getDigitArgument(helpString,i);
						maxArguments.addArgument(argument);
						helpIndex = helpIndex + help.indexOf(new Double(argument.getValue()).toString()) + new Double(argument.getValue()).toString().length();
					}
					oldIndex = i;
				}
			}
			String helpString = help.substring(oldIndex).trim();
			if(helpString.indexOf("|") != -1) {
				found = true;
				if(helpString.indexOf("<") != -1) {
					helpString = helpString.substring(helpString.indexOf("|")+1,helpString.indexOf(">")+1);
					if(helpString.indexOf(":")!=-1) {
		        String[] helpArray = helpString.substring(1,helpString.length()-1).split(":");
		        getRangeArguments(helpArray[0],helpArray[1],maxArguments);
		        Argument[] argumentsArray = (Argument[])maxArguments.getArguments();
		        for(int j = 0, m = argumentsArray.length; j < m; j++) {
		        	if(arguments.getArgument(argumentsArray[j].getName()) == null) {
		        		arguments.addArgument(argumentsArray[j]);
			        }
		        	else {
		        		maxArguments.addArgument(arguments.getArgument(argumentsArray[j].getName()));
		        	}
		        }
			    }
		      else {
		      	Argument argument = new Argument(helpString);
		        argument.setCellReference(true);
		        maxArguments.addArgument(argument);
		        if(arguments.getArgument(argument.getName()) == null) {
		        	arguments.addArgument(argument);
		        }
		        else {
		        	maxArguments.addArgument(arguments.getArgument(argument.getName()));
		        }
		      }
				}
				else {
					helpString = helpString.substring(helpString.indexOf("|")+1);
					Argument argument = getDigitArgument(helpString,index);
					helpIndex = helpIndex + help.indexOf(new Double(argument.getValue()).toString()) + new Double(argument.getValue()).toString().length();
					maxArguments.addArgument(argument);
				}
			}
			if(!found) {
				if(help.trim().startsWith("<")) {
		      String argumentBez = help.substring(help.indexOf("<"),help.indexOf(">")+1);
		      helpIndex = maxMin.length() + argumentBez.length();
		      if(argumentBez.indexOf(":")!=-1) {
		        String[] helpArray = argumentBez.substring(1,argumentBez.length()-1).split(":");
		        getRangeArguments(helpArray[0],helpArray[1],maxArguments);
		        Argument[] argumentsArray = (Argument[])maxArguments.getArguments();
		        for(int j = 0, m = argumentsArray.length; j < m; j++) {
		        	if(arguments.getArgument(argumentsArray[j].getName()) == null) {
		        		arguments.addArgument(argumentsArray[j]);
		        	}
		        	else {
		        		maxArguments.addArgument(arguments.getArgument(argumentsArray[j].getName()));
		        	}
		        }
			    }
		      else {
		      	Argument argument = new Argument(helpString);
		        argument.setCellReference(true);
		        maxArguments.addArgument(argument);
		        if(arguments.getArgument(argument.getName()) == null) {
		        	arguments.addArgument(argument);
		        }
		        else {
		        	maxArguments.addArgument(arguments.getArgument(argument.getName()));
		        }
		      }
				}
				else {
					Argument argument = getDigitArgument(help,index);
					if(argument == null) {
						return;
					}
					maxArguments.addArgument(argument);
					helpIndex = helpIndex + help.indexOf(new Double(argument.getValue()).toString()) + new Double(argument.getValue()).toString().length();
				}
			}
			maxMinElement.setArguments(maxArguments);
			elements.add(maxMinElement);
			textTableFormulaExpression.setExpression(textTableFormulaExpression.getExpression().substring(0,formulaIndex+index) + maxMin + (index+formulaIndex) + textTableFormulaExpression.getExpression().substring(formulaIndex + index + helpIndex));
			getMaxMin(maximum,help,formulaIndex + helpIndex);
		}
	}
  //----------------------------------------------------------------------------
	/**
	 * Gets the digit argument
	 * 
	 * @param helpString the string to get the argument
	 * @param index the index of the digit argument
	 * 
	 * @return the digig argument
	 * 
	 * @author Miriam Sutter
	 */
	private Argument getDigitArgument(String helpString, int index) {
		Argument argument = null;
		if(!Character.isDigit(helpString.trim().charAt(0))) {
			return null;
		}
		for(int i = 0; i < helpString.length(); i++) {
			if(Character.isDigit(helpString.charAt(i))) {
				String help = new Character(helpString.charAt(i)).toString();
				for(int j = i+1; j < helpString.length();j++) {
					if(Character.isDigit(helpString.charAt(j))) {
						help = help + new Character(helpString.charAt(j)).toString();
					}
					else if(helpString.charAt(j) == ',') {
						help = help + ".";
					}
					else if(helpString.charAt(j) == '.') {
						help = help + ".";
					}
					else if(helpString.charAt(j) != '.'){
						break;
					}
				}
				argument = new Argument("MAXMINDIGIT" + index);
				argument.setValue(helpString);
				i = i + helpString.length()-1;
				break;
			}
		}
		return argument;
	}
  //----------------------------------------------------------------------------
}
