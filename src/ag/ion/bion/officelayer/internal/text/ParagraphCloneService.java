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
 * Last changes made by $Author: markus $, $Date: 2007-11-08 12:25:20 +0100 (Do, 08 Nov 2007) $
 */
package ag.ion.bion.officelayer.internal.text;

import com.sun.star.lang.XMultiServiceFactory;

import ag.ion.bion.officelayer.internal.clone.AbstractCloneService;

import ag.ion.bion.officelayer.beans.IPropertyStore;
import ag.ion.bion.officelayer.beans.PropertyKeysContainer;
import ag.ion.bion.officelayer.clone.CloneException;
import ag.ion.bion.officelayer.clone.ClonedObject;
import ag.ion.bion.officelayer.clone.IClonedObject;
import ag.ion.bion.officelayer.clone.IDestinationPosition;

import ag.ion.bion.officelayer.text.IParagraph;
import ag.ion.bion.officelayer.text.IParagraphProperties;
import ag.ion.bion.officelayer.text.IParagraphPropertyStore;
import ag.ion.bion.officelayer.text.ITextContentService;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextRange;
import ag.ion.bion.officelayer.text.ITextService;
import ag.ion.bion.officelayer.text.TextException;

/**
 * Concrete implementation of the CloneService for paragraphs.
 * 
 * @author Sebastian Rösgen
 * @author Markus Krüger
 * @version $Revision: 11583 $
 */
public class ParagraphCloneService extends AbstractCloneService {

	private IParagraph 								paragraph 							= null;
	private ITextDocument 						document 								= null;
	private XMultiServiceFactory 			serviceFactory 					= null;
	private IParagraphPropertyStore 	paragraphPropertyStore 	= null;
	private String 										paragraphText						= null;
  //----------------------------------------------------------------------------	
	/**
	 * Constructs the ParagraphCloneService
	 * 
   * @param paragraph paragraph to be used
   * @param document  document to be used
	 * 
	 * @throws CloneException if any error occurs
	 * 
	 * @author Sebastian Rösgen
	 */
	public ParagraphCloneService (IParagraph paragraph, ITextDocument document) throws CloneException{
		this.paragraph = paragraph;
		this.document = document;
		this.serviceFactory = (XMultiServiceFactory)com.sun.star.uno.UnoRuntime.queryInterface(XMultiServiceFactory.class, document.getXTextDocument());
		try {
			analyseParagraph(paragraph);
		}
		catch(TextException excep) {
			CloneException cloneException =  new CloneException(excep.getMessage());
			cloneException.initCause(excep);
			throw cloneException;
		}
	}
  //----------------------------------------------------------------------------
	/**
	 * Clones the paragraph to the given position and then returns
	 * a reference. If the position is null, then the clone is 
	 * placed at the end of the document.
	 * 
	 * @param range range to be used
     * @param propertyKeysContainer container for property keys used for cloning style, my be null
	 * 
	 * @throws CloneException if the object could not be cloned.
	 * 
	 * @return a reference to the newly cloned element
	 */
	public IClonedObject cloneToPosition(IDestinationPosition range, PropertyKeysContainer propertyKeysContainer) throws CloneException {
		return clonePreprocessor(range, true, true,propertyKeysContainer);
	}
  //----------------------------------------------------------------------------
  /**
   * Clones the chosen object to the given position.
   * 
   * @param position the positions the object is to be cloned to
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
   * 
   * @throws CloneException if the object could not be cloned.
   * 
   * @author Markus Krüger
   */
  public void cloneToPositionNoReturn(IDestinationPosition position, PropertyKeysContainer propertyKeysContainer) throws CloneException {
    clonePreprocessor(position, true, false,propertyKeysContainer);
  }
  //----------------------------------------------------------------------------
	/**
	 * Clones the chosen paragraph to the given position and then returns
	 * a reference This method also  enables to  adopts the content of
	 * the paragraph (the default is to adopt, otherwise the paramter has
	 * to be set to false) 
	 * 
     * @param range range to be used
	 * @param adoptContent indicated if the content of the object should be adopted
     * @param propertyKeysContainer container for property keys used for cloning style, my be null
	 * 
	 * @return a reference to the newly cloned element
	 * 
	 * @throws CloneException if the object could not be cloned.
	 * 
	 * @author Sebastian Rösgen
	 */
	public IClonedObject cloneToPosition(IDestinationPosition range, boolean adoptContent, PropertyKeysContainer propertyKeysContainer) throws CloneException {
		return clonePreprocessor(range, adoptContent, true,propertyKeysContainer);
	}
  //----------------------------------------------------------------------------
  /**
   * Clones the chosen object to the given position.
   * This method also  enables to  adopts the content of
   * the object (the default is to adopt, otherwise the paramter has
   * to be set to false) 
   * 
   * @param position the positions the object is to be cloned to
   * @param adoptContent indicated if the content of the object should be adopted
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
   * 
   * @throws CloneException if the object could not be cloned.
   * 
   * @author Markus Krüger
   */
  public void cloneToPositionNoReturn(IDestinationPosition position , boolean adoptContent, PropertyKeysContainer propertyKeysContainer) throws CloneException {
    clonePreprocessor(position, adoptContent, false,propertyKeysContainer);
  }
  //----------------------------------------------------------------------------
	/**
	 * This methods clones the stored information to the submitted position (destination).
	 * 
   * @param position the destination position
	 * @param adoptContent indicated if the content of the object should be adopted
   * @param generateReturnValue indicates weahter the return value will be generate or be null
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
	 * 
	 * @return a reference to the newly cloned element
	 * 
	 * @throws CloneException if the object could not be cloned.
	 * 
	 * @author Sebastian Rösgen
	 */
	private IClonedObject clonePreprocessor(IDestinationPosition position, boolean adoptContent, boolean generateReturnValue, PropertyKeysContainer propertyKeysContainer) throws CloneException{
		try {
			ITextService service = document.getTextService();
			ITextContentService contentService = service.getTextContentService();
			if (contentService != null) {
				 IParagraph newParagraph = contentService.constructNewParagraph();
				 ITextRange range = null;
				 if (ITextRange.class.isAssignableFrom(position.getType())) {
				 	 range = (ITextRange)position.getDestinationObject();
				 }
				 if (range != null) {
				 	 contentService.insertTextContent(range, newParagraph);
				 }
				 else {
					contentService.insertTextContentAfter(paragraph,newParagraph);
				 }
				 if (adoptContent) {
				 	 newParagraph.setParagraphText(paragraphText);	
				 }
				 
         String[] propertyKeysToCopy = null;      
         if(propertyKeysContainer != null) {
           propertyKeysToCopy = propertyKeysContainer.getPropertyKeys(IParagraphProperties.TYPE_ID);        
         }
         else {
           //use default
           propertyKeysToCopy = ParagraphProperties.getDefaultPropertyKeys();
         }
         if(propertyKeysToCopy != null) {
           IParagraphProperties paragraphProperties = newParagraph.getParagraphProperties();
           ((IPropertyStore)paragraphPropertyStore).getProperties().copyTo(propertyKeysToCopy, paragraphProperties);
         }
				 
				 if(generateReturnValue)
				   return new ClonedObject(newParagraph, newParagraph.getClass());
         else
           return null;
			}
			else {
				throw new CloneException("Error constructing TextContentService to clone paragraph");
			}
		}
		catch(Exception excep) {
			CloneException cloneException =  new CloneException(excep.getMessage());
			cloneException.initCause(excep);
			throw cloneException;
		}
	}
  //----------------------------------------------------------------------------
	/**
	 * Analyses the submitted paragraph
	 * 
	 * @param paragraph the paragraph to be analysed
	 */
	private void analyseParagraph (IParagraph paragraph ) throws TextException{
		paragraphPropertyStore = paragraph.getParagraphPropertyStore();
		paragraphText = paragraph.getParagraphText();
	}
  //----------------------------------------------------------------------------
  /**
   * Clones the chosen object to the given position and then returns
   * a reference 
   * Between the given position and the newly created object
   * there will be a paragraph to add some space betwwen them. So the 
   * object WILL NOT be merged together.
   * 
   * This method is optional because it does not make sense to all possible
   * implementors of the interface. So it can happen that this method does
   * nothing more or less than the cloneToPosition method.
   * 
   * This method always adopts the content
   * 
   * @param position position to be used
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
   * 
   * @return a reference to the newly cloned element
   * 
   * @throws CloneException if the object could not be cloned.
   * 
   * @author Sebastian Rösgen
   */
  public IClonedObject cloneAfterThisPosition(IDestinationPosition position, PropertyKeysContainer propertyKeysContainer) throws CloneException {
    return cloneToPosition(position, true,propertyKeysContainer);
  }
  //----------------------------------------------------------------------------
  /**
   * Clones the chosen object to the given position.
   * Between the given position and the newly created object
   * there will be a paragraph to add some space betwwen them. So the 
   * object WILL NOT be merged together.
   * 
   * This method is optional because it does not make sense to all possible
   * implementors of the interface. So it can happen that this method does
   * nothing more or less than the cloneToPosition method.
   * 
   * This method always adopts the content
   * 
   * @param position the position the object is to be cloned after
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
   * 
   * @throws CloneException if the object could not be cloned.
   * 
   * @author Markus Krüger
   */
  public void cloneAfterThisPositionNoReturn(IDestinationPosition position, PropertyKeysContainer propertyKeysContainer) throws CloneException {
    cloneToPositionNoReturn(position, true,propertyKeysContainer);
  }
  //----------------------------------------------------------------------------
  /**
   * Clones the chosen object after the given position and then returns
   * a reference. Between the given position and the newly created object
   * there will be a paragraph to add some space betwwen them. So the 
   * object WILL NOT be merged together.
   * 
   * This method is optional because it does not make sense to all possible
   * implementors of the interface. So it can happen that this method does
   * nothing more or less than the cloneToPosition method.
   * 
   * This method also  enables to  adopts the content of
   * the object (the default is to adopt, otherwise the paramter has
   * to be set to false) 
   * 
   * @param position position to be used
   * @param adoptContent indicated if the content of the object should be adopted
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
   * 
   * @return a reference to the newly cloned element
   * 
   * @throws CloneException if the object could not be cloned.
   * 
   * @author Sebastian Rösgen
   */  
  public IClonedObject cloneAfterThisPosition(IDestinationPosition position, boolean adoptContent, PropertyKeysContainer propertyKeysContainer) throws CloneException {
    return cloneToPosition(position, adoptContent,propertyKeysContainer);
  }
  //----------------------------------------------------------------------------
  /**
   * Clones the chosen object after the given position. 
   * Between the given position and the newly created object
   * there will be a paragraph to add some space betwwen them. So the 
   * object WILL NOT be merged together.
   * 
   * This method is optional because it does not make sense to all possible
   * implementors of the interface. So it can happen that this method does
   * nothing more or less than the cloneToPosition method.
   * 
   *  This method also  enables to  adopts the content of
   * the object (the default is to adopt, otherwise the paramter has
   * to be set to false) 
   * 
   * @param position the position the object is to be cloned after
   * @param adoptContent indicated if the content of the object should be adopted
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
   * 
   * @throws CloneException if the object could not be cloned.
   * 
   * @author Markus Krüger
   */  
  public void cloneAfterThisPositionNoReturn(IDestinationPosition position, boolean adoptContent, PropertyKeysContainer propertyKeysContainer) throws CloneException {
    cloneToPositionNoReturn(position, adoptContent,propertyKeysContainer);
  }
  //----------------------------------------------------------------------------
 
}