package com.LogisticsCompany.enums;
/**
 * Enum defining the different types of accounts within the system.
 * Each account type represents a specific role with associated permissions and access levels.
 *
 * - CLIENT: Represents a customer or client of the service, with access to consumer-facing features.
 * - EMPLOYEE: Represents a staff member of the company, with access to internal operational tools and information.
 * - COMPANY_MANAGER: Represents a manager within the company, with elevated permissions to manage company-related operations.
 * - ADMIN: Represents an administrator with the highest level of access, capable of managing all aspects of the system.
 * - OFFICE_MANAGER: Represents a manager of a specific office or branch, with permissions to manage local operations.
 */
public enum AccountType {

    CLIENT, EMPLOYEE,COMPANY_MANAGER, ADMIN , OFFICE_MANAGER
}
