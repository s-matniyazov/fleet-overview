package fleetoverview.util.constants;

/**
 * @author :  qobiljon.qodirjonov
 * @mailto :  1810092kk@gmail.com
 * @created : 28 янв. 2025
 **/
public interface UriConstants {
    String API = "/api";
    String AUTH = "/api/auth";

    String MENUS = API + "/menus";

    String USERS = API + "/users";
    String DEPARTMENTS = API + "/departments";
    String POSITIONS = API + "/positions";
    String ROLES = API + "/roles";
    String ACTIONS = API + "/actions";

    String PROJECTS = API + "/projects";
    String PROJECT_CATEGORIES = API + "/project/categories";
    String PROJECT_STATUSES = API + "/project/statuses";
    String PROJECT_PRIORITIES = API + "/project/priorities";

    String TEAMS = API + "/teams";
    String TEAM_MEMBERS = API + "/team/members";

    String TASKS = API + "/tasks";
    String TASK_TYPES = API + "/task/types";
    String TASK_STATUSES = API + "/task/statuses";
    String TASK_PRIORITIES = API + "/task/priorities";
    String TASK_CALENDAR = API + "/task/calendar";

    String CLIENTS = API + "/clients";
    String MODULES = API + "/modules";
}
