package iSpaceAutoGrouping;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String username = args[0];
		String password = args[1];
		
		WebClient webClient = new WebClient();
		WebClientOptions webClientOptions = webClient.getOptions();
		webClientOptions.setJavaScriptEnabled(false);
		webClientOptions.setCssEnabled(false);
		webClientOptions.setTimeout(35000);
		webClientOptions.setThrowExceptionOnScriptError(false);
		webClientOptions.setThrowExceptionOnFailingStatusCode(false);
		
		try {
			HtmlPage page = webClient.getPage("http://ispace.uic.edu.hk/login/index.php");
			HtmlForm form = page.getFormByName("");
			HtmlTextInput usernameInput = form.getInputByName("username");
			HtmlPasswordInput passwordInput = form.getInputByName("password");
			usernameInput.setText(username);
			passwordInput.setText(password);
			List<HtmlInput> buttons = form.getInputsByName("");
			HtmlInput button = buttons.get(0);
			button.click();
			int pageNum = 0;
			HtmlDivision paging;
			do {
				System.out.println("PageNumber = " + pageNum);
				page = webClient.getPage("http://ispace.uic.edu.hk/enrol/users.php?id=6&role=5&page="+pageNum+"&perpage=100&sort=lastname&dir=ASC");
				
				List<HtmlTableRow> hr = (List<HtmlTableRow>) page.getByXPath("//tr[@class='userinforow r1'] | //tr[@class='userinforow r0'] | //tr[@class='userinforow r1 lastrow'] | //tr[@class='userinforow r0 lastrow']");
				List<HtmlDivision> groupingDiv = (List<HtmlDivision>) page.getByXPath("//div[@class='groups']");
				List<HtmlDivision> roleDiv = (List<HtmlDivision>) page.getByXPath("//div[@class='roles']");
				List<HtmlDivision> groupDiv = (List<HtmlDivision>) page.getByXPath("//div[@class='enrolment']");
				
				
				String id = "";
				HtmlPage addGroup;
				String groupNum = "";
				HtmlSelect groupSelect;
				
				for(int i = 0; i < hr.size(); i++){
					//System.out.println(hr.get(i).asText());
					//HtmlDivision g = hr.get(i).asText();
					if (groupingDiv.get(i).asText().equals("")){
						if(roleDiv.get(i).asText().equals("Student")) {
							id = hr.get(i).getAttribute("id").substring(5);
							groupNum = groupDiv.get(i).asText().split("[(]")[2];
							groupNum = groupNum.split("[)]")[0];
							
							addGroup = webClient.getPage("http://ispace.uic.edu.hk/enrol/users.php?id=6&page=0&perpage=100&sort=lastname&dir=ASC&action=addmember&user=" + id);
							form = addGroup.getFormByName("");
							groupSelect = form.getSelectByName("groupid");
							int tmpi = Integer.parseInt(groupNum)+92;
							groupSelect.setSelectedAttribute(tmpi+"", true);
							button = form.getInputByName("submitbutton");
							button.click();
							
							System.out.println(i);
						}
					}
				}
				pageNum++;
				paging = (HtmlDivision) page.getByXPath("//div[@class='paging']").get(0);
			} while (paging.asText().contains("Next"));
			
			
			webClient.closeAllWindows();

		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
