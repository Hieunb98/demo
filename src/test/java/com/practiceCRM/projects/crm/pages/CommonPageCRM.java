package com.practiceCRM.projects.crm.pages;

import com.practiceCRM.keywords.WebUI;
import com.practiceCRM.projects.crm.pages.Clients.ClientPageCRM;
import com.practiceCRM.projects.crm.pages.Dashboard.DashboardPageCRM;
import com.practiceCRM.projects.crm.pages.Projects.ProjectPageCRM;
import com.practiceCRM.projects.crm.pages.Login.LoginPageCRM;
import com.practiceCRM.projects.crm.pages.Events.EventPageCRM;
import com.practiceCRM.projects.crm.pages.Leads.LeadPageCRM;
import com.practiceCRM.projects.crm.pages.Tickets.TicketPageCRM;
import com.practiceCRM.projects.crm.pages.Invoices.InvoicePageCRM;
import com.practiceCRM.projects.crm.pages.TeamMembers.TeamMemberPageCRM;
import com.practiceCRM.projects.crm.pages.Candidates.CandidatePageCRM;
import com.practiceCRM.projects.crm.pages.Circulars.CircularPageCRM;
import com.practiceCRM.projects.crm.pages.Tasks.TaskPage;
import com.practiceCRM.constants.FrameworkConstants;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import static com.practiceCRM.keywords.WebUI.*;

public class CommonPageCRM {

    public LoginPageCRM loginPageCRM;
    public DashboardPageCRM dashboardPage;
    public ClientPageCRM clientPage;
    public ProjectPageCRM projectPage;
    public TaskPage taskPage;
    public EventPageCRM eventPage;
    public LeadPageCRM leadPage;
    public TicketPageCRM ticketPage;
    public InvoicePageCRM invoicePage;
    public TeamMemberPageCRM teamMemberPage;
    public CandidatePageCRM candidatePage;
    public CircularPageCRM circularPage;

    // =====================================================================
    // SIDEBAR NAVIGATION LOCATORS
    // =====================================================================
    public By menuDashboard = By.cssSelector("#sidebar-menu a[href*='/dashboard']");
    public By menuEvents = By.cssSelector("#sidebar-menu a[href*='/events']");
    public By menuClients = By.cssSelector("#sidebar-menu a[href*='/clients']");
    public By menuProjects = By.cssSelector("#sidebar-menu a[href*='/projects/all_projects']");
    public By menuTasks = By.cssSelector("#sidebar-menu a[href*='/tasks/all_tasks']");
    public By menuLeads = By.cssSelector("#sidebar-menu a[href*='/leads']");
    public By menuTickets = By.cssSelector("#sidebar-menu a[href*='/tickets']");
    public By menuSalesParent = By.xpath("//ul[@id='sidebar-menu']//a[contains(.,'Sales')]");
    public By menuSalesInvoices = By.cssSelector("#sidebar-menu a[href*='/invoices']");
    public By menuTeamParent = By.xpath("//ul[@id='sidebar-menu']//a[contains(@href,'/team_members') and normalize-space()='Team']");
    public By menuTeamMembersSubItem = By.xpath("//ul[@id='sidebar-menu']//a[contains(@href,'/team_members') and normalize-space()='Team members']");
    public By menuTeamMembers = By.cssSelector("#sidebar-menu a[href*='/team_members']");
    public By menuRecruitments = By.cssSelector("#sidebar-menu a[href*='/recruitment']");
    public By menuRecruitmentCandidates = By.cssSelector("#sidebar-menu a[href*='/recruitment_candidates']");
    public By menuRecruitmentCirculars = By.xpath("//ul[@id='sidebar-menu']//a[normalize-space()='Circulars']");

    // =====================================================================
    // TOP BAR LOCATORS
    // =====================================================================
    public By topbarBtnTodo = By.cssSelector("a[href*='/todo']");
    public By topbarBtnStarredProjects = By.cssSelector("a[data-real-target='#projects-quick-list-container']");
    public By topbarBtnStarredClients = By.cssSelector("a[data-real-target='#clients-quick-list-container']");
    public By topbarBtnDashboards = By.cssSelector("a[data-real-target='#my-dashboards-list-container']");
    public By topbarBtnGlobalSearch = By.id("global-search-btn");
    public By topbarBtnQuickAdd = By.id("quick-add-icon");
    public By topbarBtnLanguage = By.id("personal-language-icon");
    public By topbarBtnReminders = By.id("reminder-icon");
    public By topbarBtnNotifications = By.id("web-notification-icon");
    public By topbarBtnMessages = By.id("message-notification-icon");

    // Dropdown items
    public By topbarQuickAddTask = By.id("js-quick-add-task");
    public By topbarQuickAddEvent = By.id("js-quick-add-event");

    public By dropdownAccount = By.id("user-dropdown");
    public By buttonSignOut = By.xpath("//a[normalize-space()='Sign Out']");

    public LoginPageCRM signOut() {
        clickElement(dropdownAccount);
        clickElement(buttonSignOut);
        return new LoginPageCRM();
    }

    // =====================================================================
    // TOP BAR METHODS
    // =====================================================================

    @Step("Click To Do on Top Bar")
    public void clickTopbarTodo() {
        clickElement(topbarBtnTodo);
    }

    @Step("Click Starred Projects on Top Bar")
    public void clickTopbarStarredProjects() {
        clickElement(topbarBtnStarredProjects);
    }

    @Step("Click Starred Clients on Top Bar")
    public void clickTopbarStarredClients() {
        clickElement(topbarBtnStarredClients);
    }

    @Step("Click Dashboards on Top Bar")
    public void clickTopbarDashboards() {
        clickElement(topbarBtnDashboards);
    }

    @Step("Click Global Search on Top Bar")
    public void clickTopbarGlobalSearch() {
        clickElement(topbarBtnGlobalSearch);
    }

    @Step("Click Quick Add on Top Bar")
    public void clickTopbarQuickAdd() {
        clickElement(topbarBtnQuickAdd);
    }

    @Step("Click Language on Top Bar")
    public void clickTopbarLanguage() {
        clickElement(topbarBtnLanguage);
    }

    @Step("Click Reminders on Top Bar")
    public void clickTopbarReminders() {
        clickElement(topbarBtnReminders);
    }

    @Step("Click Notifications on Top Bar")
    public void clickTopbarNotifications() {
        clickElement(topbarBtnNotifications);
    }

    @Step("Click Messages on Top Bar")
    public void clickTopbarMessages() {
        clickElement(topbarBtnMessages);
    }

    public LoginPageCRM getSignInPage() {
        if (loginPageCRM == null) {
            loginPageCRM = new LoginPageCRM();
        }
        return loginPageCRM;
    }

    public DashboardPageCRM getDashboardPage() {
        if (dashboardPage == null) {
            dashboardPage = new DashboardPageCRM();
        }
        return dashboardPage;
    }

    public ClientPageCRM getClientPage() {
        if (clientPage == null) {
            clientPage = new ClientPageCRM();
        }
        return clientPage;
    }

    public ProjectPageCRM getProjectPage() {
        if (projectPage == null) {
            projectPage = new ProjectPageCRM();
        }
        return projectPage;
    }

    public TaskPage getTaskPage() {
        if (taskPage == null) {
            taskPage = new TaskPage();
        }
        return taskPage;
    }

    public EventPageCRM getEventPage() {
        if (eventPage == null) {
            eventPage = new EventPageCRM();
        }
        return eventPage;
    }

    public LeadPageCRM getLeadPage() {
        if (leadPage == null) {
            leadPage = new LeadPageCRM();
        }
        return leadPage;
    }

    public TicketPageCRM getTicketPage() {
        if (ticketPage == null) {
            ticketPage = new TicketPageCRM();
        }
        return ticketPage;
    }

    public InvoicePageCRM getInvoicePage() {
        if (invoicePage == null) {
            invoicePage = new InvoicePageCRM();
        }
        return invoicePage;
    }

    public TeamMemberPageCRM getTeamMemberPage() {
        if (teamMemberPage == null) {
            teamMemberPage = new TeamMemberPageCRM();
        }
        return teamMemberPage;
    }

    public CandidatePageCRM getCandidatePage() {
        if (candidatePage == null) {
            candidatePage = new CandidatePageCRM();
        }
        return candidatePage;
    }

    public CircularPageCRM getCircularPage() {
        if (circularPage == null) {
            circularPage = new CircularPageCRM();
        }
        return circularPage;
    }

    // =====================================================================
    // COMMON ACTION METHODS (SIDEBAR & TOP BAR)
    // =====================================================================

    @Step("Mở trang Dashboard trực tiếp qua URL")
    public DashboardPageCRM openDashboardPage() {
        openWebsite(FrameworkConstants.URL_CRM.replace("/signin", "/dashboard"));
        waitForPageLoaded();
        verifyContains(getCurrentUrl(), getDashboardPage().pageUrl, "Không thể điều hướng đến trang Dashboard.");
        return getDashboardPage();
    }

    @Step("Click menu sidebar: Dashboard")
    public DashboardPageCRM clickMenuDashboard() {
        clickElement(menuDashboard);
        waitForPageLoaded();
        verifyContains(getCurrentUrl(), "/dashboard", "Điều hướng đến Dashboard thất bại.");
        return getDashboardPage();
    }

    @Step("Click menu sidebar: Events")
    public EventPageCRM clickMenuEvents() {
        clickElement(menuEvents);
        waitForPageLoaded();
        verifyContains(getCurrentUrl(), getEventPage().pageUrl, "Điều hướng đến Events thất bại.");
        return getEventPage();
    }

    @Step("Click menu sidebar: Clients")
    public ClientPageCRM clickMenuClients() {
        clickElement(menuClients);
        waitForPageLoaded();
        verifyContains(getCurrentUrl(), getClientPage().pageUrl, "Điều hướng đến Clients thất bại.");
        return getClientPage();
    }

    @Step("Click menu sidebar: Projects")
    public ProjectPageCRM clickMenuProjects() {
        clickElement(menuProjects);
        waitForPageLoaded();
        waitForElementVisible(getProjectPage().tableProjects, 10);
        verifyContains(getCurrentUrl(), com.practiceCRM.projects.crm.models.ProjectModel.PAGE_URL, "Điều hướng đến Projects thất bại.");
        return getProjectPage();
    }

    @Step("Click menu sidebar: Tasks")
    public TaskPage clickMenuTasks() {
        clickElement(menuTasks);
        waitForPageLoaded();
        verifyContains(getCurrentUrl(), getTaskPage().pageUrl, "Điều hướng đến Tasks thất bại.");
        return getTaskPage();
    }

    @Step("Click menu sidebar: Leads")
    public LeadPageCRM clickMenuLeads() {
        clickElement(menuLeads);
        waitForPageLoaded();
        verifyContains(getCurrentUrl(), getLeadPage().pageUrl, "Điều hướng đến Leads thất bại.");
        return getLeadPage();
    }

    @Step("Click menu sidebar: Tickets")
    public TicketPageCRM clickMenuTickets() {
        clickElement(menuTickets);
        waitForPageLoaded();
        verifyContains(getCurrentUrl(), getTicketPage().pageUrl, "Điều hướng đến Tickets thất bại.");
        return getTicketPage();
    }

    @Step("Mở sub-menu Sales rồi click Invoices")
    public InvoicePageCRM clickMenuSalesInvoices() {
        clickElement(menuSalesParent);
        waitForPageLoaded();
        clickElement(menuSalesInvoices);
        waitForPageLoaded();
        verifyContains(getCurrentUrl(), getInvoicePage().pageUrl, "Điều hướng đến Invoices thất bại.");
        return getInvoicePage();
    }

    @Step("Mở sub-menu Team rồi click Team members")
    public TeamMemberPageCRM clickMenuTeamMembers() {
        clickElement(menuTeamParent);
        waitForPageLoaded();
        clickElement(menuTeamMembersSubItem);
        waitForPageLoaded();
        verifyContains(getCurrentUrl(), getTeamMemberPage().pageUrl, "Điều hướng đến Team members thất bại.");
        return getTeamMemberPage();
    }

    @Step("Mở sub-menu Recruitments rồi click Candidates")
    public CandidatePageCRM clickMenuRecruitmentCandidates() {
        clickElement(menuRecruitments);
        waitForPageLoaded();
        clickElement(menuRecruitmentCandidates);
        waitForPageLoaded();
        verifyContains(getCurrentUrl(), getCandidatePage().pageUrl, "Điều hướng đến Candidates thất bại.");
        return getCandidatePage();
    }

    @Step("Mở sub-menu Recruitments rồi click Circulars")
    public CircularPageCRM clickMenuRecruitmentCirculars() {
        clickElement(menuRecruitments);
        waitForPageLoaded();
        clickElement(menuRecruitmentCirculars);
        waitForPageLoaded();
        verifyContains(getCurrentUrl(), getCircularPage().pageUrl, "Điều hướng đến Circulars thất bại.");
        return getCircularPage();
    }

}
