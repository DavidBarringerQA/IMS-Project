
# Table of Contents



<table border="2" cellspacing="0" cellpadding="6" rules="groups" frame="hsides">


<colgroup>
<col  class="org-left" />

<col  class="org-left" />

<col  class="org-left" />

<col  class="org-left" />

<col  class="org-left" />

<col  class="org-left" />

<col  class="org-right" />
</colgroup>
<thead>
<tr>
<th scope="col" class="org-left"><b>Risk</b></th>
<th scope="col" class="org-left"><b>Statement</b></th>
<th scope="col" class="org-left"><b>Response</b></th>
<th scope="col" class="org-left"><b>Objective</b></th>
<th scope="col" class="org-left"><b>Likelihood</b></th>
<th scope="col" class="org-left"><b>Impact</b></th>
<th scope="col" class="org-right"><b>Risk Level</b></th>
</tr>
</thead>

<tbody>
<tr>
<td class="org-left">SQL Injection</td>
<td class="org-left">Inputs could modify the database, removing or modifying entries</td>
<td class="org-left">Using prepared statements for user input</td>
<td class="org-left">Properly escaping user input to stop injection attacks from succeeding</td>
<td class="org-left">Possible</td>
<td class="org-left">Severe</td>
<td class="org-right">10</td>
</tr>
</tbody>

<tbody>
<tr>
<td class="org-left">Dropped Database</td>
<td class="org-left">Developers accidentally dropping the database, causing all entries to be deleted</td>
<td class="org-left">Using a sepereate database for testing, keep regular backups</td>
<td class="org-left">Preventing the most likely causes of the database dropping and add a contingency if it does happen</td>
<td class="org-left">Unlikely</td>
<td class="org-left">Major</td>
<td class="org-right">4</td>
</tr>
</tbody>

<tbody>
<tr>
<td class="org-left">Code Gets Removed</td>
<td class="org-left">The code for running the program gets removed from the machine that hosts it</td>
<td class="org-left">Keep tagged releases in a seperate repositoty (github)</td>
<td class="org-left">Providing a means of reobtaining the program from somewhere that it can't be easily removed</td>
<td class="org-left">Unlikely</td>
<td class="org-left">Major</td>
<td class="org-right">4</td>
</tr>
</tbody>

<tbody>
<tr>
<td class="org-left">Incorrect input</td>
<td class="org-left">The input on the command line doesn't fit the design of the database</td>
<td class="org-left">Add constraints to database, allow for updating items</td>
<td class="org-left">Constraints help prevent bad inputs from being stored, the update query allows bad inputs to be corrected</td>
<td class="org-left">Expected</td>
<td class="org-left">Minor</td>
<td class="org-right">8</td>
</tr>
</tbody>

<tbody>
<tr>
<td class="org-left">Host Computer/s fails</td>
<td class="org-left">The computer/server responsible for hosting the code/database stop working</td>
<td class="org-left">Have backups of project and database allowed elsewhere</td>
<td class="org-left">If the computer/server needs replacing, the system can be set back up easily and is up-to-date</td>
<td class="org-left">Unlikely</td>
<td class="org-left">Severe</td>
<td class="org-right">5</td>
</tr>
</tbody>

<tbody>
<tr>
<td class="org-left">&#xa0;</td>
<td class="org-left">&#xa0;</td>
<td class="org-left">&#xa0;</td>
<td class="org-left">&#xa0;</td>
<td class="org-left">&#xa0;</td>
<td class="org-left">&#xa0;</td>
<td class="org-right">&#xa0;</td>
</tr>
</tbody>
</table>

