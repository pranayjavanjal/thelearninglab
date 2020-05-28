
var header_code='<div class="">'+
		'<a href="#">'+
			'<img th:src="@{img/site-logo.jpg}" alt="The Learning Lab" id="logo" data-height-percentage="54" data-actual-width="169" data-actual-height="72" style="width: 22%">'+
		'</a>'+
			'<div class="profile_details w3l" style="float:right;">'+		
				'<ul>'+
				'<li class="dropdown profile_details_drop">'+
						'<a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">'+
							'<div class="profile_img">'+	
								'<span class="prfil-img"><img th:src="@{/img/profile.jpg}" alt=""> </span>'+ 
								'<div class="user-name">'+
									'<p sec:authentication="name"></p>'+
									'<span sec:authentication="principal.authorities"></span>'+
								'</div>'+
								'<i class="fa fa-angle-down"></i>'+
								'<i class="fa fa-angle-up"></i>'+
								'<div class="clearfix"></div>'+
							'</div>	'+
						'</a>'+
						'<ul class="dropdown-menu drp-mnu">'+ 
							'<li> <a th:href="@{/user/editUserProfile}"><i class="fa fa-user"></i> Profile</a> </li>'+ 
							'<li> <a th:href="@{/logout}"><i class="fa fa-sign-out"></i> Logout</a> </li>'+
						'</ul>'+
					'</li>'+
				'</ul>'+
			'</div>'+
			'<h2><a th:href="@{/user/home}">The Learning Lab</a></h2>'+
		'</div>'+
				
	     '<div class="clearfix"> </div>';