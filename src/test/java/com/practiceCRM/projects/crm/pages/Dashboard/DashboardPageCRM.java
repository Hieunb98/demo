package com.practiceCRM.projects.crm.pages.Dashboard;

import static com.practiceCRM.keywords.WebUI.*;

import com.practiceCRM.constants.FrameworkConstants;
import com.practiceCRM.enums.FailureHandling;
import com.practiceCRM.projects.crm.pages.Clients.ClientPageCRM;
import com.practiceCRM.projects.crm.pages.CommonPageCRM;
import com.practiceCRM.projects.crm.pages.Projects.ProjectPageCRM;
import com.practiceCRM.projects.crm.pages.Tasks.TaskPage;
import com.practiceCRM.projects.crm.pages.Events.EventPageCRM;
import com.practiceCRM.projects.crm.pages.Leads.LeadPageCRM;
import com.practiceCRM.projects.crm.pages.Tickets.TicketPageCRM;
import com.practiceCRM.projects.crm.pages.Invoices.InvoicePageCRM;
import com.practiceCRM.projects.crm.pages.TeamMembers.TeamMemberPageCRM;
import com.practiceCRM.projects.crm.pages.Candidates.CandidatePageCRM;
import com.practiceCRM.projects.crm.pages.Circulars.CircularPageCRM;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * DashboardPageCRM - Page Object cho màn hình Dashboard của RISE CRM.
 * Tất cả locators được thu thập từ DOM thực tế (verified).
 */
public class DashboardPageCRM extends CommonPageCRM {

    public DashboardPageCRM() {
        super();
    }

    public String pageText = "Dashboard";
    public String pageUrl = "/dashboard";
    public String pageTitle = "Dashboard | RISE CRM | Anh Tester Demo";

    // =====================================================================
    // CLOCK IN / CLOCK OUT LOCATORS (Dashboard specific)
    // =====================================================================
    public By btnClockOut = By.xpath("//a[normalize-space()='Clock Out']");
    public By textClockStarted = By.xpath("//*[contains(text(),'Clock started at')]");

    // =====================================================================
    // TOP BAR LOCATORS (WIDGETS ONLY)
    // =====================================================================
    public By widgetMyOpenTasks = By.cssSelector("a[href*='my_open_tasks']");
    public By widgetMyOpenTasksCount = By.cssSelector("a[href*='my_open_tasks'] h1");

    public By widgetEventsToday = By.xpath("//a[contains(@href,'/events')][contains(.,'Events today')]");
    public By widgetEventsTodayCount = By.xpath("//a[contains(@href,'/events')][contains(.,'Events today')]//h1");

    public By widgetDue = By.xpath("//a[contains(@href,'/invoices')][contains(.,'Due')]");
    public By widgetDueAmount = By.xpath("//a[contains(@href,'/invoices')][contains(.,'Due')]//h1");

    // =====================================================================
    // PROJECTS OVERVIEW WIDGET LOCATORS
    // =====================================================================
    public By widgetProjectsOverview = By.xpath("//div[contains(@class,'card-header') or contains(@class,'panel-heading')][contains(.,'Projects Overview')]");
    public By projectsOpenCount = By.cssSelector("a[href*='/projects/all_projects/1'] h4");
    public By projectsCompletedCount = By.cssSelector("a[href*='/projects/all_projects/2'] h4");
    public By projectsHoldCount = By.cssSelector("a[href*='/projects/all_projects/3'] h4");
    public By projectsProgressBar = By.xpath(
            "//a[contains(@href,'/projects/all_projects/1')]/ancestor::div[contains(@class,'clearfix')]//div[@role='progressbar']");

    // =====================================================================
    // INVOICE OVERVIEW WIDGET LOCATORS
    // =====================================================================
    public By widgetInvoiceOverview = By.xpath("//div[contains(@class,'card-header') or contains(@class,'panel-heading')][contains(.,'Invoice Overview')]");
    public By invoiceLinkOverdue = By.cssSelector("a[href*='overdue']");
    public By invoiceLinkNotPaid = By.cssSelector("a[href*='not_paid']");
    public By invoiceLinkPartiallyPaid = By.cssSelector("a[href*='partially_paid']");
    public By invoiceLinkFullyPaid = By.cssSelector("a[href*='fully_paid']");
    public By invoiceLinkDraft = By.cssSelector("a[href*='draft']");
    public By invoiceTotalInvoicedLabel = By.xpath("//div[contains(@class, 'card-header') and contains(., 'Invoice Overview')]/following-sibling::div[contains(@class,'card-body')]//*[contains(., 'Total invoiced')]");
    public By invoiceDueLabel = By
            .xpath("//div[contains(@class, 'card-header') and contains(., 'Invoice Overview')]/following-sibling::div[contains(@class,'card-body')]//*[contains(., 'Due')]");

    // =====================================================================
    // INCOME VS EXPENSES WIDGET LOCATORS
    // =====================================================================
    public By widgetIncomeExpenses = By.xpath("//div[contains(@class,'card-header') or contains(@class,'panel-heading')][contains(.,'Income vs Expenses')]");
    public By incomeExpensesThisYear = By
            .xpath("//div[contains(@class, 'card-header') and contains(., 'Income vs Expenses')]/following-sibling::div[contains(@class,'card-body')]//*[contains(., 'This Year')]");
    public By incomeExpensesLastYear = By
            .xpath("//div[contains(@class, 'card-header') and contains(., 'Income vs Expenses')]/following-sibling::div[contains(@class,'card-body')]//*[contains(., 'Last Year')]");

    // =====================================================================
    // ALL TASKS OVERVIEW WIDGET LOCATORS
    // =====================================================================
    public By widgetAllTasksOverview = By.xpath("//div[contains(@class,'card-header') or contains(@class,'panel-heading')][contains(.,'All Tasks Overview')]");
    public By tasksLinkToDo = By.cssSelector("a[href*='tasks_list/1/0']");
    public By tasksLinkDone = By.cssSelector("a[href*='tasks_list/3/0']");
    public By tasksLinkExpired = By.cssSelector("a[href*='all_tasks_overview/expired']");
    public By tasksLinkCritical = By.cssSelector("a[href*='tasks_list/0/3']");
    public By tasksLinkMinor = By.cssSelector("a[href*='tasks_list/0/1']");

    // =====================================================================
    // TEAM MEMBERS OVERVIEW WIDGET LOCATORS
    // =====================================================================
    public By widgetTeamMembersOverview = By.xpath("//div[contains(@class,'card-header') or contains(@class,'panel-heading')][contains(.,'Team Members Overview')]");
    public By teamMembersCountLink = By.cssSelector("a[href*='/team_members']");
    public By teamOnLeaveTodayLink = By.cssSelector("a[href*='/leaves']");
    public By teamClockedInLink = By.cssSelector("a[href*='members_clocked_in']");
    public By teamClockedOutLink = By.cssSelector("a[href*='clock_in_out']");
    public By announcementsEmptyState = By.xpath("//*[normalize-space()='No announcement yet!']");

    // =====================================================================
    // TICKET STATUS WIDGET LOCATORS
    // =====================================================================
    public By widgetTicketStatus = By.xpath("//div[contains(@class,'card-header') or contains(@class,'panel-heading')][contains(.,'Ticket Status')]");
    public By ticketLinkNew = By.cssSelector("a[href*='/tickets/index/new']");
    public By ticketLinkOpen = By.cssSelector("a[href*='/tickets/index/open']");
    public By ticketLinkClosed = By.cssSelector("a[href*='/tickets/index/closed']");

    // =====================================================================
    // PROJECT TIMELINE & OPEN PROJECTS WIDGET LOCATORS
    // =====================================================================
    public By widgetProjectTimeline = By.xpath("//div[contains(@class,'card-header') or contains(@class,'panel-heading')][contains(.,'Project Timeline')]");
    public By timelineEntries = By.xpath("//div[contains(@class, 'card-header') and contains(., 'Project Timeline')]/following-sibling::div//p");
    public By widgetOpenProjects = By.xpath("//div[contains(@class,'card-header') or contains(@class,'panel-heading')][contains(.,'Open Projects')]");
    public By openProjectsProgressBar = By
            .xpath("//div[contains(@class, 'card-header') and contains(., 'Open Projects')]/following-sibling::div[contains(@class,'card-body')]//div[@role='progressbar'][1]");

    // =====================================================================
    // EVENTS EMPTY STATE LOCATOR
    // =====================================================================
    public By eventsEmptyState = By.xpath("//*[normalize-space()='No event found!']");

    // =====================================================================
    // ACTION METHODS
    // =====================================================================

    @Step("Lấy số lượng 'My open tasks' từ Top Bar")
    public String getMyOpenTasksCount() {
        return getTextElement(widgetMyOpenTasksCount);
    }

    @Step("Click widget 'My open tasks'")
    public TaskPage clickWidgetMyOpenTasks() {
        clickElement(widgetMyOpenTasks);
        waitForPageLoaded();
        verifyContains(getCurrentUrl(), "/tasks/all_tasks", "Click widget My open tasks không điều hướng đúng.");
        return new TaskPage();
    }

    @Step("Lấy số lượng 'Events today' từ Top Bar")
    public String getEventsTodayCount() {
        return getTextElement(widgetEventsTodayCount);
    }

    @Step("Click widget 'Events today'")
    public EventPageCRM clickWidgetEventsToday() {
        clickElement(widgetEventsToday);
        waitForPageLoaded();
        verifyContains(getCurrentUrl(), "/events", "Click widget Events today không điều hướng đúng.");
        return new EventPageCRM();
    }

    @Step("Lấy số tiền 'Due' từ Top Bar")
    public String getDueAmount() {
        return getTextElement(widgetDueAmount);
    }

    @Step("Click widget 'Due'")
    public InvoicePageCRM clickWidgetDue() {
        clickElement(widgetDue);
        waitForPageLoaded();
        verifyContains(getCurrentUrl(), "/invoices/index", "Click widget Due không điều hướng đúng.");
        return new InvoicePageCRM();
    }

    @Step("Kiểm tra widget Projects Overview hiển thị")
    public boolean isProjectsOverviewDisplayed() {
        waitForPageLoaded();
        return verifyElementPresent(widgetProjectsOverview, 15, "Widget Projects Overview không hiển thị.");
    }

    @Step("Lấy số lượng dự án Open")
    public String getProjectsOpenCount() {
        return getTextElement(projectsOpenCount);
    }

    @Step("Kiểm tra widget Invoice Overview hiển thị")
    public boolean isInvoiceOverviewDisplayed() {
        return verifyElementPresent(widgetInvoiceOverview, 15, "Widget Invoice Overview không hiển thị.");
    }

    @Step("Click link Invoice trạng thái: Overdue")
    public InvoicePageCRM clickInvoiceOverdue() {
        clickElement(invoiceLinkOverdue);
        waitForPageLoaded();
        verifyContains(getCurrentUrl(), "overdue", "Click Overdue không điều hướng đúng.");
        return new InvoicePageCRM();
    }

    @Step("Click link Invoice trạng thái: Not paid")
    public InvoicePageCRM clickInvoiceNotPaid() {
        clickElement(invoiceLinkNotPaid);
        waitForPageLoaded();
        verifyContains(getCurrentUrl(), "not_paid", "Click Not paid không điều hướng đúng.");
        return new InvoicePageCRM();
    }

    @Step("Click link Invoice trạng thái: Partially paid")
    public InvoicePageCRM clickInvoicePartiallyPaid() {
        clickElement(invoiceLinkPartiallyPaid);
        waitForPageLoaded();
        verifyContains(getCurrentUrl(), "partially_paid", "Click Partially paid không điều hướng đúng.");
        return new InvoicePageCRM();
    }

    @Step("Click link Invoice trạng thái: Fully paid")
    public InvoicePageCRM clickInvoiceFullyPaid() {
        clickElement(invoiceLinkFullyPaid);
        waitForPageLoaded();
        verifyContains(getCurrentUrl(), "fully_paid", "Click Fully paid không điều hướng đúng.");
        return new InvoicePageCRM();
    }

    @Step("Click link Invoice trạng thái: Draft")
    public InvoicePageCRM clickInvoiceDraft() {
        clickElement(invoiceLinkDraft);
        waitForPageLoaded();
        verifyContains(getCurrentUrl(), "draft", "Click Draft không điều hướng đúng.");
        return new InvoicePageCRM();
    }

    @Step("Kiểm tra widget All Tasks Overview hiển thị")
    public boolean isAllTasksOverviewDisplayed() {
        return verifyElementPresent(widgetAllTasksOverview, 15, "Widget All Tasks Overview không hiển thị.");
    }

    @Step("Kiểm tra widget Team Members Overview hiển thị")
    public boolean isTeamMembersOverviewDisplayed() {
        return verifyElementPresent(widgetTeamMembersOverview, 15, "Widget Team Members Overview không hiển thị.");
    }

    @Step("Kiểm tra widget Ticket Status hiển thị")
    public boolean isTicketStatusDisplayed() {
        return verifyElementPresent(widgetTicketStatus, 15, "Widget Ticket Status không hiển thị.");
    }

    @Step("Click link Ticket trạng thái: New")
    public TicketPageCRM clickTicketNew() {
        clickElement(ticketLinkNew);
        waitForPageLoaded();
        verifyContains(getCurrentUrl(), "/tickets/index/new", "Click Ticket New không điều hướng đúng.");
        return new TicketPageCRM();
    }

    @Step("Kiểm tra widget Project Timeline hiển thị")
    public boolean isProjectTimelineDisplayed() {
        return verifyElementPresent(widgetProjectTimeline, 15, "Widget Project Timeline không hiển thị.");
    }

    @Step("Kiểm tra widget Open Projects hiển thị với progress bar")
    public boolean isOpenProjectsDisplayed() {
        return verifyElementPresent(widgetOpenProjects, 15, "Widget Open Projects không hiển thị.");
    }

    @Step("Kiểm tra hiển thị Empty State 'No event found!'")
    public boolean isEventsEmptyStateDisplayed() {
        return verifyElementPresent(eventsEmptyState, 15, "Empty state 'No event found!' không hiển thị.");
    }

    @Step("Kiểm tra hiển thị Empty State 'No announcement yet!'")
    public boolean isAnnouncementsEmptyStateDisplayed() {
        return verifyElementPresent(announcementsEmptyState, 15, "Empty state 'No announcement yet!' không hiển thị.");
    }

    @Step("Kiểm tra hiển thị đầy đủ tất cả các widgets chính trên Dashboard")
    public void verifyAllDashboardWidgetsPresent() {
        FailureHandling softAssert = FailureHandling.CONTINUE_ON_FAILURE;
        By[] mainWidgets = {
                widgetProjectsOverview,
                widgetInvoiceOverview,
                widgetIncomeExpenses,
                widgetAllTasksOverview,
                widgetTeamMembersOverview,
                widgetTicketStatus,
                widgetProjectTimeline,
                widgetOpenProjects
        };
        String[] widgetNames = {
                "Projects Overview",
                "Invoice Overview",
                "Income vs Expenses",
                "All Tasks Overview",
                "Team Members Overview",
                "Ticket Status",
                "Project Timeline",
                "Open Projects"
        };
        for (int i = 0; i < mainWidgets.length; i++) {
            verifyElementPresent(mainWidgets[i], 5, "Widget '" + widgetNames[i] + "' không hiển thị trên Dashboard.",
                    softAssert);
        }
    }

    // =====================================================================
    // LEGACY METHODS (backward compatibility)
    // =====================================================================
    public DashboardPageCRM openDashboardPageLegacy() {
        clickElement(menuDashboard);
        return this;
    }

    public ClientPageCRM openClientPage() {
        clickElement(menuClients);
        return new ClientPageCRM();
    }

    public ProjectPageCRM openProjectPage() {
        clickElement(menuProjects);
        return new ProjectPageCRM();
    }

    public TaskPage openTaskPage() {
        clickElement(menuTasks);
        return new TaskPage();
    }
}
