package com.infy.autoqa.karate.PageClasses;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.infy.autoqa.karate.helperclass.TestBase;

import com.infy.autoqa.exceptions.TimeOutException;

/**
 * @Description
 * @Author gmathavx
 * @Since 21-Sep-2018
 */

public class AccountsPageClass extends TestBase {

	public AccountsPageClass() {
		PageFactory.initElements(seleniumObj.getDriver(), this);
	}

	@FindBy(xpath = "//a[@title='New']")
	public WebElement newButton;

	@FindBy(xpath = "//nav[@role='navigation']/ol/li/span[text() = 'Accounts']")
	public WebElement accountsHomePageHeader;

	@FindBy(xpath = "//h2[text()='New Account: CoMarketing']")
	public WebElement accountsCreationPageHeader;

	@FindBy(xpath = "//div[contains(@class,'modal-container')]//label[contains(.,'Account Name')]/following-sibling::input")
	public WebElement accountName;

	@FindBy(xpath = "//div[contains(@class,'modal-container')]//input[@title='Search Channel Programs']")
	public WebElement channelProgram;

	@FindBy(xpath = "//div[contains(@class,'modal-container')]//input[@title='Search Accounts']")
	public WebElement parentFundAccount;

	@FindBy(xpath = "//div[contains(@class,'modal-container')]//span[text()='Parent Account']/parent::div/following-sibling::div")
	public WebElement parentAccount;

	@FindBy(xpath = "//div[contains(@class,'modal-container')]//div[contains(@class,'recordTypeName')]")
	public WebElement accountRecordType;

	@FindBy(xpath = "//div[contains(@class,'modal-container')]//span[text()='Account Owner']//ancestor::div[contains(@class,'field-label-container')]/following-sibling::div//span[contains(@class,'uiOutputText')]")
	public WebElement accountOwner;

	public WebElement dynamicPopupDropdown(String value) {
		String xpath = "//div[contains(@class,'modal-container')]//div[contains(@class,'primaryLabel') and contains(@title,'"+ value +"')]";
		return seleniumObj.waitForElement(By.xpath(xpath), 30, 1);
		}
	
	public WebElement autodynamicPopupDropdown(String value) {
		String xpath = "//div[contains(@class,'slds-m-left--smalllabels slds-truncate slds-media__body')]//div[contains(@class,'primaryLabel') and (@title='"+ value +"')]";
		return seleniumObj.waitForElement(By.xpath(xpath), 30, 1);
		}
	
	/*	public WebElement dynamicPopupDropdown(String value) {
	String xpath = "//div[contains(@class,'modal-container')]//div[contains(@class,'lookup__menu') and not(contains(@class,'invisible'))]//ul[@class='lookup__list  visible']//div[@title='"
			+ value + "']/ancestor::a";
	return seleniumObj.waitForElement(By.xpath(xpath), 30, 1);
	}  */	

	@FindBy(xpath = "//div[contains(@class,'modal-container')]//button[@title='Cancel']")
	public WebElement cancelButton;

	@FindBy(xpath = "//div[contains(@class,'modal-container')]//button[@title='Save']")
	public WebElement saveButton;

	@FindBy(xpath = "//div[contains(@class,'modal-container')]//button[@title='Save & New']")
	public WebElement saveAndNewButton;

	@FindBy(xpath = "//div[contains(@class,'windowViewMode-normal')]//h1//div[contains(@class,'slds-page-header')]")
	public WebElement accountNameTitle;

	@FindBy(xpath = "//div[contains(@class,'modal-container')]//span[contains(@class,'genericError')]")
	public WebElement genericErrorMessage;

	@FindBy(xpath = "//div[contains(@class,'modal-container')]//ul[contains(@class,'errorsList')]")
	public WebElement genericListErrorMessage;

	@FindBy(xpath = "//div[contains(@class,'modal-container')]//input[@title='Search Accounts']//ancestor::div[@class='slds-form-element__control']//ul[contains(@class,'uiInputDefaultError ')]/li")
	public WebElement parentFundAccountErrorMessage;

	@FindBy(xpath = "//div[contains(@class,'modal-container')]//label[contains(.,'Account Name')]/ancestor::div[@class='slds-form-element__control']//ul[contains(@class,'uiInputDefaultError ')]/li")
	public WebElement accountNameErrorMessage;

	@FindBy(xpath = "//div[contains(@class,'modal-container')]//input[@title='Search Channel Programs']//ancestor::div[@class='slds-form-element__control']//ul[contains(@class,'uiInputDefaultError ')]/li")
	public WebElement channelProgramsErrorMessage;

	@FindBy(xpath = "//h2[text()='New Account: CoMarketing']//parent::div//parent::div//label//span[not(contains(@class,'required'))]")
	public List<WebElement> accountsLablesList;

	@FindBy(xpath = "//div[contains(@class,'slds-page-header__title')]")
	public WebElement accountTitle;

	@FindBy(xpath = "//span[contains(text(),'CoMarketing')]/../../div[1]/span")
	public WebElement comarketingRadioButton;

	@FindBy(xpath = "//div[@class='forceChangeRecordTypeFooter']//span[contains(text(),'Next')]")
	public WebElement nextButton;

	@FindBy(xpath = "//span[text()='Parent Fund Account']/ancestor::div/following-sibling::div[contains(@class, 'slds-form-element')]//a")
	public WebElement parentFundAccountInDetailsPage;

	@FindBy(xpath = "//div[contains(@class,'windowViewMode-normal')]//a[@title='Details']")
	public WebElement pmbDetails;

	@FindBy(xpath = "//span[text()='Enabled Auto-Transfer'][1]//following::input[@type='checkbox']")
	public WebElement autoTransferCheckbox;
	
	@FindBy(xpath = "(//span[text()='Enabled Auto-Transfer']//following::img)[1]")
	public WebElement verifyautoTransferCheckbox;
	
	@FindBy(xpath = "(//div[contains(@class,'autocompleteWrapper slds-grow')]//input[@title='Search Accounts'])[2]")
	public WebElement autoTransferAccount;
	
	@FindBy(xpath = "//button[@title='Edit Enabled Auto-Transfer']")
	private WebElement editnabledAutoTransfer;
	
	@FindBy(xpath = "(//span[text()='Budget Amount'][1]//following::div[@class='slds-form-element__control slds-grid itemBody']//following::span[@class='forceOutputCurrency'])[1]")
	public WebElement budgetAmount;
	
	@FindBy(xpath = "//div[contains(@class,'button-container slds-text-align_center forceRecordEditActions')]//button[@title='Save']")
	public WebElement forceRecordEditActionsSaveButton;
	
	@FindBy(xpath = "//span[text()='Save']")
	private WebElement actionSaveButton;
	
	@FindBy(xpath = "(//span[text()='Press Delete to Remove'])[3]/../span[@class='deleteIcon']")
	public WebElement deleteautoTransferAccount;
	
	public List<WebElement> accountBankMapping() {
		String xpath = "//table[@data-aura-class='uiVirtualDataTable']//tbody//tr//a[@class='slds-truncate outputLookupLink slds-truncate forceOutputLookup']";
		return seleniumObj.getDriver().findElements(By.xpath(xpath));
	}

	public WebElement accountBankMappingViewAll() {
		String xpath = "//h2//span[text()='Account Bank Mappings']/ancestor::div[@class='container forceRelatedListSingleContainer']//span[@class='view-all-label']";
		return seleniumObj.getDriver().findElement(By.xpath(xpath));
	}

	public WebElement newButtonInAccountBankMapping() {
		String xpath = "//div[@class='slds-grid']//div[@title='New']";
		return seleniumObj.getDriver().findElement(By.xpath(xpath));
	}

	public WebElement bankInputSearch() {
		String xpath = "//span[text()='Bank']//ancestor::div[@class='slds-form-element__control']//input[contains(@title,'Search')]";
		return seleniumObj.getDriver().findElement(By.xpath(xpath));
	}

	public WebElement searchResult(String SearchText, String SearchType) {
		return seleniumObj.getDriver().findElement(By.xpath("//div[@class='listContent']//a//div[@title='" + SearchText
				+ "']/following-sibling::div[@title='" + SearchType + "']"));
	}

	@FindBy(xpath = "//button[@title='Save']")
	public WebElement saveButtonInAccountBankMapping;

	public WebElement accountInSearchResult(String account) {
		String xpath = "//table/tbody//tr//th//a[text()='" + account + "']";
		return seleniumObj.waitForElement(By.xpath(xpath), 5, 10);

	}

	@FindBy(xpath = "//input[@name='Account-search-input']")
	public WebElement searchFieldInListView;

	public void clickOnNewButton() {
		seleniumObj.click(newButton);
	}

	public void setAccountName(String accountNameValue) {
		seleniumObj.sendKeys(accountName, accountNameValue);
	}

	public void setChannelProgram(String channelProgramValue) {
		seleniumObj.sendKeys(channelProgram, channelProgramValue);
	}

	public void setParentFundAccountValue(String parentFundAccountValue) {
		parentFundAccount.clear();
		seleniumObj.sendKeys(parentFundAccount, parentFundAccountValue);
		seleniumObj.waitForSeconds(4);
		seleniumObj.sendKeys(parentFundAccount, Keys.SPACE);
		seleniumObj.waitForSeconds(4);
		seleniumObj.sendKeys(parentFundAccount, Keys.BACK_SPACE);
	}
	
	public void setAutoTransferAccountValue(String autoTransferAccountValue) {
		autoTransferAccount.clear();		
		seleniumObj.sendKeys(autoTransferAccount, autoTransferAccountValue);
		seleniumObj.waitForSeconds(4);
		seleniumObj.sendKeys(autoTransferAccount, Keys.SPACE);
		seleniumObj.waitForSeconds(4);
		seleniumObj.sendKeys(autoTransferAccount, Keys.BACK_SPACE);
	}	
	
	
	public void selectDynamicPopupDropdown(String dynamicDropdownValue) throws InterruptedException {
		seleniumObj.waitForSeconds(8);
		seleniumObj.click(dynamicPopupDropdown(dynamicDropdownValue));

	}	
	
	public void selectAutoDynamicPopupDropdown(String dynamicDropdownValue) throws InterruptedException {
		seleniumObj.waitForSeconds(8);
		seleniumObj.click(autodynamicPopupDropdown(dynamicDropdownValue));

	}
	
	public void clickOnSaveButton() {
		seleniumObj.click(saveButton);
	}

	public void clickOnSaveAndNewButton() {
		seleniumObj.click(saveAndNewButton);
	}

	public void clickOnCancelButton() {
		seleniumObj.click(cancelButton);
	}

	public String getAccountNameTitle() {
		return accountNameTitle.getText();
	}

	public String getGenericErrorMessage() {
		return genericErrorMessage.getText();
	}

	public String getGenericListErrorMessage() {
		return genericListErrorMessage.getText();
	}

	public String getAccountNameErrorMessage() {
		return accountNameErrorMessage.getText();
	}

	public String getChannelProgramsErrorMessage() {
		return channelProgramsErrorMessage.getText();
	}

	public String getParentFundAccountErrorMessage() {
		return parentFundAccountErrorMessage.getText();
	}

	public String getAccountTitle() {
		return accountTitle.getText();
	}

	public List<WebElement> coMarketingAccouctViewColumnHeader() {
		String xpath = "//table/thead//th//a/span[@class='slds-truncate']";
		return seleniumObj.getDriver().findElements(By.xpath(xpath));

	}

	public List<WebElement> coMarketingAccountRows() {
		String xpath = "//div[@data-aura-class='forceListViewManagerGrid']//table/tbody//tr";
		return seleniumObj.getDriver().findElements(By.xpath(xpath));

	}

	public WebElement coMarketingAccountViewRows(int rowIndex, int columnindex) {
		String xpath = "//div[@data-aura-class='forceListViewManagerGrid']//table/tbody//tr[" + rowIndex + "]/td["
				+ columnindex + "]//a";
		return seleniumObj.waitForElement(By.xpath(xpath), 10, 1);

	}

	public WebElement selectColumnHeader(String columName) {
		String xpath = "//table/thead//tr//th[@title='" + columName + "']";
		return seleniumObj.waitForElement(By.xpath(xpath), 10, 3);
	}

	public void clickOnComarketingButton() throws TimeOutException {
		seleniumObj.waitForWebElementVisible(comarketingRadioButton, 15);
		seleniumObj.click(comarketingRadioButton);
	}

	public void clickOnNextButton() throws TimeOutException {
		seleniumObj.waitForWebElementVisible(nextButton, 15);
		seleniumObj.click(nextButton);
	}
	
	public void clickOnEditSaveButton() {
		seleniumObj.click(saveButton);
	}

	/**
	 * @Description Method to get 'Parent Fund Account' in details page
	 * @Author vveeranx
	 * @Since Dec 06, 2018
	 * @return String
	 */
	public String getParentFundAccount() throws TimeOutException {
		return parentFundAccountInDetailsPage.getText();
	}

	/**
	 * @Description Method to click on 'Details' tab
	 * @Author vveeranx
	 * @Since Dec 06, 2018
	 * @throws TimeOutException
	 */
	public void clickOnDetailsTab() throws TimeOutException {
		pmbDetails.click();
	}

	/**
	 * 
	 * @Description Method enter text in bank search text box
	 * @Author ubijux
	 * @param searchText
	 * @Since Dec 09, 2018
	 */
	public void setValueForbankSearchTextbox(String searchText) {
		try {
			bankInputSearch().click();
			bankInputSearch().clear();
			bankInputSearch().sendKeys(searchText);
			log.info("Entered text in bank search textbox : " + searchText);
		} catch (Exception e) {
			log.error("Not able to enter text in bank search text box");
			Assert.fail("Not able to enter text in bank search text box");
		}

	}

	/**
	 * 
	 * @Description Method click on search text with search type in auto-complete
	 *              list view
	 * @Author ubijux
	 * @Since Dec 09, 2018
	 * @param searchResultText
	 * @param SearchType
	 */
	public void clickOnSearchResult(String searchResultText, String SearchType) {
		try {
			searchResult(searchResultText, SearchType).click();
			log.info("Clicked on search text : " + searchResultText + " with search type : " + SearchType
					+ " in auto-complete list view");
		} catch (Exception e) {
			log.error("Not able click on search result with search type in auto-complete list view");
			Assert.fail("Not able click on search result with search type in auto-complete list view");
		}

	}

	
	/**
	 * @Description method to click on new button in account bank mapping page
	 * @author ubijux
	 * @since Dec 09 , 2018
	 */

	public void clickOnNewButtonInAccountBankMapping() {
		newButtonInAccountBankMapping().click();
	}

	/**
	 * @Description method to click on save button in account bank mapping page
	 * @author ubijux
	 * @since Dec 09 , 2018
	 */

	public void clickOnSaveButtonInAccountBankMapping() {
		saveButtonInAccountBankMapping.click();
	}

	/**
	 * Method to verify the account is displayed in search result
	 * 
	 * @Author prachivx
	 * @Since Jan 03, 2019
	 * @Param account to be searched in the result list
	 * @return boolean whether the account is displayed in the search result or not
	 */
	public boolean isAccountDispalyed(String account) {
		return sfcommonObj.checkElementExists(accountInSearchResult(account));
	}

	/**
	 * Method to search for an account
	 * 
	 * @Author prachivx
	 * @Since Jan 03, 2019
	 * @param account is used to search for a account in 'accounts' page
	 */
	public void searchForAccountInList(String account) {
		seleniumObj.clearText(searchFieldInListView);
		seleniumObj.sendKeys(searchFieldInListView, account);
		seleniumObj.sendKeys(searchFieldInListView, Keys.RETURN);
		sfcommonObj.waitTillAllXHRCallsComplete();
	}

	/**
	 * @Description method to get AccountName
	 * @author shivikax
	 * @since Jan 10, 2019
	 * @return boolean whether the account name is displayed or not after successful
	 *         creation
	 */
	public boolean checkAccountNameDisplayed(String accountName) {
		boolean visible = false;
		try {
			WebElement account = seleniumObj.waitForElement(By.xpath("//h1/div[@title='" + accountName + "']"), 5, 3);
			visible = seleniumObj.isElementExists(account);
		} catch (Exception e) {
			log.info(accountName + " Account Name not found");
			Assert.fail("Not able to find the Account Name");
			visible = false;
		}
		log.info("Account Name display check method is completed");
		return visible;
	}
	
	/**
	 * @Description method to click on Auto Transfer check Box
	 * @author dravix
	 * @since May 21 , 2019
	 */

	public void clickOnAutoTransferCheckBox() {
		autoTransferCheckbox.click();
	}
	
	public WebElement clickOnAutoTransferCheckBoxs() {
		String xpath = "//span[text()='Enabled Auto-Transfer'][1]//following::input[@type='checkbox']";
		return seleniumObj.waitForElement(By.xpath(xpath), 30, 1);
	}
	
	public WebElement gverifyautoTransferCheckbox() {
		String xpath = "(//span[text()='Enabled Auto-Transfer']//following::img)[1]";
		return seleniumObj.waitForElement(By.xpath(xpath), 30, 1);
	}
	

	/**
	 * @Description Method to get 'Parent Fund Account' in details page
	 * @Author dravix
	 * @Since May21, 2018
	 * @return String
	 */
	public String getBudgetAmount() throws TimeOutException {
		return budgetAmount.getText();
	}
	
	/**
	 * @Description method to edit enabled on Auto Transfer check Box
	 * @author dravix
	 * @since May 21 , 2019
	 */

	public void clickonEditAutoTransfer() {
		editnabledAutoTransfer.click();
	}
	
	/**
	 * 
	 * @Description Method to check if element enabled
	 * @Author vveeranx
	 * @Since Nov 27, 2018
	 */
	public Boolean checkElementEnabled(WebElement element) {
		return sfcommonObj.checkElementExists(element);
	}
	
	
	/**
	 * 
	 * @Description Method to select activity
	 * @Author vveeranx
	 * @Since Nov 27, 2018
	 * @Param activity
	 */
	public void selectAccount(String account) throws TimeOutException {
		autoTransferAccount.click();
		autoTransferAccount.clear();
		autoTransferAccount.sendKeys(account);

		sfcommonObj.waitTillAllXHRCallsComplete();
		String xpath = "//div[contains(text(), '" + account + "')]";
		WebElement accountName = seleniumObj.waitForElement(By.xpath(xpath), 10, 5);
		accountName.click();
		sfcommonObj.waitTillAllXHRCallsComplete();
	}
	public void clickOnAccountSaveButton() {
		seleniumObj.click(actionSaveButton);
	}
	public void deleteAutotransferAccount() {
		seleniumObj.click(deleteautoTransferAccount);
	}
	
	public String getAutoTransferCheckBox() {
		return verifyautoTransferCheckbox.getAttribute("class");
	}
	
	/**
	 * 
	 * @Description Method to select programName
	 * @Author prachivx
	 * @Since Oct 30, 2018
	 * @param programName
	 */
	public void selectPartnerProgramfromlookup(String programName) throws InterruptedException {
		try {
			seleniumObj.waitForElement(programLookup, 10, 5);
			programLookup.click();
			programLookup.clear();
			programLookup.sendKeys(programName);
			seleniumObj.waitForSeconds(1);
			programLookup.sendKeys(Keys.SPACE);
			seleniumObj.waitForSeconds(2);
			programLookup.sendKeys(Keys.BACK_SPACE);
			sfcommonObj.waitTillLightningPageLoadComplete();
			
			WebElement partner = seleniumObj.waitForElement(By.xpath("//div[@title = '" + programName + "']"), 5, 3);
			partner.click();
			
			/*for (WebElement ele : programList) {
				seleniumObj.waitForElement(ele, 2, 2);
				if (ele.getAttribute("title").equals(programName)) {
					ele.click();
					break;
				}
			}*/
			log.info("SelectProgramfromlookup method completed");
		} catch (Exception ex) {
			try {
				seleniumObj.waitForElement(programLookup, 10, 5);
				programLookup.click();
				programLookup.clear();
				programLookup.sendKeys(programName);
				seleniumObj.waitForSeconds(1);
				programLookup.sendKeys(Keys.SPACE);
				seleniumObj.waitForSeconds(2);
				programLookup.sendKeys(Keys.BACK_SPACE);
				sfcommonObj.waitTillLightningPageLoadComplete();
				/*for (WebElement ele : programList) {
					seleniumObj.waitForElement(ele, 2, 2);
					if (ele.getAttribute("title").equals(programName)) {
						ele.click();
						break;
					}
					log.info("SelectProgramfromlookup method completed");
				}*/
				
				WebElement partner = seleniumObj.waitForElement(By.xpath("//div[@title = '" + programName + "']"), 5, 3);
				partner.click();

			} catch (Exception exp) {
				Assert.fail("Unable to Select the " + programName + " from the screen and error : " + exp.getMessage());
			}
		}
	}
	@FindBy(xpath = "//input[@placeholder='Search Partner Programs...']")
	public WebElement programLookup;
}
