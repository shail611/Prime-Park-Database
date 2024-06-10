# Prime Park Database
 A database created in MySQL capable of managing parking problems in major locations

# Objective
The objective of the Prime Parking Management System is to create a database for efficiently
managing parking lots and their users on a university campus. Each parking area is subdivided
into parking lots, zones, and spaces. Each space has a unique space number, a space type, and an
availability status. Drivers are categorized as students, employees, or visitors, identified by their
ID or phone number. Drivers can obtain permits specifying the zone, space type, license number,
and permit type. Citations are issued for parking violations, varying based on different
categories. Administrators have the authority to manage parking lots, assign zones and spaces,
issue permits, check permit validity, and handle citation-related tasks. Users must possess a
permit matching the lot's designated zones and space types to park. Users are limited to one
permit for students and visitors and up to two permits for employees. The system's core tasks
include information processing, permit and vehicle maintenance, citation generation, and report
generation for various parking-related data, ensuring effective parking management on campus.

# Stakeholders
- DriverInformation: name, status(student, employee, visitor), ID
- Zone Information: zone ID
- Space Information: space number, space type, availability status.
- Citation Records: citation number, car license number, citation date, citation time, lot,
category, fee, payment status.
- Permit Information: permit ID, lot, zone ID, space number, start date, expiration date,
expiration time, driverId, and permit type, carLicenseNumber

# Design Decisions
The system has a main menu that gives users the following options: “Information Processing”,
“Maintaining permits and vehicle information for each driver”, “Generating and maintaining
citations”, “Reports”, “Exit”. When prompted, the user enters a number 0-5 corresponding with
what kind of action they would like to take. For each menu option there is a submenu displayed
that has specific operations listed. The system has a main class (Main.java) that facilitates
switching between the main menu options and separate classes for each related group of
operations. This was done to break the application into more manageable and maintainable
pieces of code.

The program also contains two helper classes. One helper class is DBManager, which, through
the use of a shared database connection, provides the ability to execute SQL queries and perform
transactions through various utility methods. The other class is Input, which is a class that
attempts to abstract the details of getting different types of input from the user, and includes a
method for printing a ResultSet object.

# Assumptions

● Parking lots consist of Zones. Zone Id is unique within a parking lot. Zones consist
of spaces. Each space has a space number which is unique within a Zone.

● UnivID and phone number never clash. Single ID attribute can be used to store
UnivID or phone number based on the status of the driver. UnivID is stored in the
ID attribute in case of a student or employee and phone number is stored in case of
a visitor.

● A vehicle can have at most one permit at any time.

● A permit is given for a particular space number in a particular zone in a particular
parking lot.

● All appeals for parking citations are handled in person. Drivers must visit the
security office, where they can submit their appeal and have it resolved on the
spot.

● It is mandatory for the permit card to be visibly displayed on the vehicle whenever
it is parked. Security personnel will identify parking violations by inspecting the
displayed permit cards periodically.

● When a security guard records a citation in the database, they will also affix a
physical ticket to the vehicle. This ticket contains details about the violation and
provides instructions for payment.

● Drivers have the option to initiate the payment process online by entering the
citation number, which is found on the ticket. They can then proceed to pay the
required fine based on the violation.

● Only those vehicle information are stored that are currently on a permit or have
had permits previously. Vehicle ownership information is not explicitly stored.
Driver holding a permit currently is considered the owner of the vehicle.

● Handicap users getting 50% discount on all citations will be handled at the
operations level.

● Restrictions on number of permits and number of vehicles on each permit based
on the status of the driver will be handled at operations level

● One driver can make only one appeal per citation.

● A Vehicle cannot have multiple permits at a time.

● In the case of ‘No permit’, the security guard will manually check whether the vehicle
has permit or not, if not, then the security guard will first request the administrator to add
vehicle and driver details into the system and then security will manually add a citation
for that particular vehicle by asking for all the information regarding vehicle and driver
on the spot. (We are doing this because it might happen that in the case of no permit the
drivers and vehicle’s details might not be there into the system and the citation table is
linked with the vehicle's table so the foreign key constraint should be satisfied).

● When user raises a request for permit, we will check all the constraints based on user
status and then if everything is ok then we will give parking permission to the user at
particular location(parking lot, zone and space type), at this time hence manually
checking every available space in the parking lot is not possible, we will randomly select
any of the empty parking spaces and change its status to ‘NO’ instead of ‘YES’, which
will help us to keep track of currently available parking spaces.

● A space is randomly selected and availability status is updated to NO in order to handle
the permit overflow case in a space type in a particular zone and parking lot.

● Admin updates the Availability status of the space back to YES once that permit expires.

● The permit’s start date should only be the date on which the permit is being requested.
