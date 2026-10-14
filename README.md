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

Diagrama de Secuencia.
```mermaid
sequenceDiagram
    actor User
    actor Admin
    participant App as Booking Web App
    participant DB as Database

    Note over User,DB: App enforces roles and ownership on every request

    User->>App: View available resources
    App->>DB: Get unreserved resources for requested period
    DB-->>App: Available resources
    App-->>User: Show available resources only

    User->>App: View my bookings
    App->>DB: Get bookings owned by current user
    DB-->>App: Own bookings
    App-->>User: Show own bookings only

    User->>App: Create booking
    App->>DB: Save booking with owner and status = pending
    DB-->>App: Booking created
    App-->>User: Booking pending admin approval

    alt User cancels before approval
        User->>App: Cancel own pending booking
        App->>App: Verify booking ownership
        App->>DB: Set status = cancelled
        DB-->>App: Booking cancelled
        App-->>User: Confirm cancellation
    else Admin approves booking
        Admin->>App: Approve pending booking
        App->>App: Verify admin role and resource availability
        App->>DB: Set status = confirmed and reserve resource
        DB-->>App: Booking confirmed
        App-->>Admin: Confirm approval
        opt User cancels after approval
            User->>App: Cancel own confirmed booking
            App->>App: Verify booking ownership
            App->>DB: Set status = cancelled and release reservation
            DB-->>App: Booking cancelled
            App-->>User: Confirm cancellation
        end
    end

    opt Admin views all users, bookings and resources
        Admin->>App: View all users, bookings or resources
        App->>App: Verify admin role
        App->>DB: Retrieve requested list
        DB-->>App: Requested list
        App-->>Admin: Show complete list
    end

    opt Admin manages resources, reservations, and users
        Admin->>App: Create, modify, or delete resource, reservation, or user
        App->>App: Verify admin role
        App->>DB: Apply requested operation
        DB-->>App: Operation completed
        App-->>Admin: Confirm change
    end
```
