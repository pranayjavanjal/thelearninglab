document.write("<div class=\"col-sm-3 col-xs-6 sidebar pl-0\">"+
               " <div class=\"inner-sidebar mr-3\">"+
               "     <!--Image Avatar static data-->"+
               "     <div class=\"avatar text-center\">"+
               "         <img src=\"/img/profile.jpeg\" alt=\"\" class=\"rounded-circle\" />"+
               "         <p><strong>Pranay Javanjal</strong></p>"+
               "         <span class=\"text-primary small\"><strong>Java Developer</strong></span>"+
               "     </div>"+
               "     <!--Image Avatar-->"+
               "     <!--Sidebar Navigation Menu-->"+
               "     <div class=\"sidebar-menu-container\">"+
               "         <ul class=\"sidebar-menu mt-4 mb-4\">"+
               "             <li class=\"parent\">"+
               "                 <a  th:href=\"@{/user/home}\" onclick=\"toggle_menu('dashboard'); return false\" class=\"\"><i class=\"fa fa-dashboard mr-3\"> </i>"+
               "				<span class=\"none\">Dashboard <i class=\"fa fa-angle-down pull-right align-bottom\"></i></span>"+
               "                 </a>"+
               "                 <ul class=\"children\" id=\"dashboard\">"+
               "                     <li class=\"child\"><a href=\"index.html\" class=\"ml-4\"><i class=\"fa fa-angle-right mr-2\"></i> Default</a></li>"+
               "                     <li class=\"child\"><a href=\"index2.html\" class=\"ml-4\"><i class=\"fa fa-angle-right mr-2\"></i> Analytics</a></li>"+
               "                     <li class=\"child\"><a href=\"index3.html\" class=\"ml-4\"><i class=\"fa fa-angle-right mr-2\"></i> Ecommerce</a></li>"+
               "                    <li class=\"child\"><a href=\"index4.html\" class=\"ml-4\"><i class=\"fa fa-angle-right mr-2\"></i> Cryptocurrency</a></li>"+
               "                 </ul>"+
               "             </li>"+
               "             </li>"+
               "            <!--  <li class=\"parent\">"+
               "                 <a href=\"widgets.html\" class=\"\"><i class=\"fa fa-puzzle-piece mr-3\"></i>"+
               "                     <span class=\"none\">Widget </span>"+
               "                 </a>"+
               "             </li> -->"+
               "            <li class=\"parent\" sec:authorize=\"hasAuthority('0')\">"+
			   "					<a href=\"#\" onclick=\"toggle_menu('admin-panel'); return false\" class=\"\"><i class=\"fa fa-users\"></i>"+
			   "					<span class=\"none\">Admin Panel <i class=\"fa fa-angle-down pull-right align-bottom\"></i></span>"+
			   "				</a>"+
				"				<ul class=\"children\" id=\"admin-panel\">"+
			"						<li class=\"child\"><a href=\"/user/add-leaves\" class=\"ml-4\"><i class=\"fa fa-angle-right mr-2\"></i> Assign Leaves</a></li>"+
			"						<li class=\"child\"><a href=\"/user/leaves-status\" class=\"ml-4\"><i class=\"fa fa-angle-right mr-2\"></i> Leaves Status</a></li>"+
			"						<li class=\"child\"><a href=\"/user/manage-users\" class=\"ml-4\"><i class=\"fa fa-angle-right mr-2\"></i> Manage Users</a></li>"+
			"						<li class=\"child\"><a href=\"/registration\" class=\"ml-4\"><i class=\"fa fa-angle-right mr-2\"></i> Register User</a></li>"+
			"					</ul>"+
			"				</li>"+
			"				<li class=\"parent\">"+
			"					<a href=\"#\" onclick=\"toggle_menu('manage-leaves'); return false\" class=\"\"><i class=\"fas fa-list-alt\"></i>"+
			"						<span class=\"none\">Manage Leaves <i class=\"fa fa-angle-down pull-right align-bottom\"></i></span>"+
			"					</a>"+
			"					<ul class=\"children\" id=\"manage-leaves\">"+
			"						<li class=\"child\"><a th:href=\"@{/user/apply-leave}\" class=\"ml-4\"><i class=\"fa fa-angle-right mr-2\"></i> Apply Leave</a></li>"+
			"						<li class=\"child\"  class=\"parent\" sec:authorize=\"hasAuthority('0')\"><a th:href=\"@{/user/manage-leaves}\" class=\"ml-4\"><i class=\"fa fa-angle-right mr-2\"></i> Manage Leave</a></li>"+
			"						<li class=\"child\"><a th:href=\"@{/user/my-leaves}\" class=\"ml-4\"><i class=\"fa fa-angle-right mr-2\"></i> My Leave</a></li>"+
			"					</ul>"+
			"				</li>"+
			"				<li class=\"parent\">"+
             "                   <a href=\"fullcalendar.html\" class=\"\"><i class=\"fa fa-calendar-o mr-3\"> </i>"+
              "                      <span class=\"none\">Full Calendar </span>"+
               "                 </a>"+
                "            </li>"+
                 "           <li class=\"parent\">"+
                  "              <a  th:href=\"@{/user/change-password}\" class=\"\"><i class=\"fa fa-key\"> </i>"+
                   "                 <span class=\"none\">Change Password</span>"+
                    "            </a>"+
                     "       </li>"+
                      "  </ul>"+
                    "</div>"+
                    "<!--Sidebar Naigation Menu-->"+
                "</div>"+
            "</div>"+
            "<!--Sidebar left-->");