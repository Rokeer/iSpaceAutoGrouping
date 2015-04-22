1. Unzip htmlunit library (htmlunit-2.15.zip) and import into this project.
2. Login iSpace, and open IT course. The address should be looks like "http://ispace.uic.edu.hk/course/view.php?id=1870", remember the course id. In this example, it is 1870.
3. Create groups for each section from "User" -> "Groups", then go to "Enrolled users" page.
4. Click on the button that used for assign groups, and you will enter a new page. View source code of this page, and find the group id for the first group. You should looking for something like "<option value="764">IT_Group_01</option>". In this example the first group id is 764. Please make sure, the option values for each group is sequential, for exapmle, option value for IT_Group_01 is 764, then option value for IT_Group_02 must be 765, 766 for IT_Group_03, and so on.
5. Back to our program. 4 arguments were required to run this program. The first is your iSpace username, the second is your iSpace password, the third is course id (it is 1870 in this example), the fourth is group id (it is 764 in this example).
6. Run it, sit back, and relax.

Contact me any time, my email is me[this is at]colinzhang.com