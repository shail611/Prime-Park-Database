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
