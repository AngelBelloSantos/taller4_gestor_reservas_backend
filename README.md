Entities Diagram.
```mermaid
classDiagram
direction TB
    class Resource {
	    +string name
	    +string detail
	    +int sharedCapacity
	    +ResourceStatus status
    }

    class ResourceStatus {
	    Operational
	    OutOfService
	    Retired
    }

    class ResourceType {
	    +string name
    }

    class User {
	    +string name
	    +string identificationNumber
	    +string phoneNumber
	    +UserRole role
    }

    class UserRole {
	    Root
	    Admin
	    StandardUser
    }

    class Address {
	    +string streetName
	    +string streetNumber
    }

    class Reservation {
	    +dateTime startDate
	    +dateTime endDate
    }

	<<enumeration>> ResourceStatus
	<<enumeration>> UserRole

    Resource "1" o-- "0..*" Resource : contains
    ResourceType "1" <-- "0..*" Resource : categorizes
    User "1" *-- "1" Address : has
    User "1" <-- "0..*" Reservation : makes
    Resource "1..*" <-- "0..*" Reservation : reserves
```

User Case.
```mermaid
usecase-beta
actor User("User")
actor Admin("Admin")
Login("Log in")
ManageUsers("Manage users")
ManageOwnUserProfile("Manage user's own profile")
ManageResources("Manage resources")
ViewAvailableResources("View available resources")
ManageReservations("Manage reservations")
ManageOwnReservations("Manage user's reservations")
User --> Login
User --> ManageOwnUserProfile
User --> ViewAvailableResources
User --> ManageOwnReservations
Admin --> ManageUsers
Admin --> ManageResources
Admin --> ManageReservations
```

