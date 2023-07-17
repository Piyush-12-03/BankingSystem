import java.util.*;

class BankAccount {
    private String accountNumber;
    private String accountHolder;
    protected double balance;

    public BankAccount(String accountNumber, String accountHolder) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = 0.0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit of $" + amount + " successful. Current balance: $" + balance);
        } else {
            System.out.println("Invalid amount. Deposit failed.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0) {
            if (balance >= amount) {
                balance -= amount;
                System.out.println("Withdrawal of $" + amount + " successful. Current balance: $" + balance);
            } else {
                System.out.println("Insufficient balance. Withdrawal failed.");
            }
        } else {
            System.out.println("Invalid amount. Withdrawal failed.");
        }
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber + ", Account Holder: " + accountHolder + ", Balance: $" + balance;
    }
}

class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(String accountNumber, String accountHolder, double interestRate) {
        super(accountNumber, accountHolder);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void applyInterest() {
        double interest = balance * interestRate / 100;
        deposit(interest);
        System.out.println("Interest of $" + interest + " applied. Current balance: $" + balance);
    }

    @Override
    public String toString() {
        return "Savings Account - " + super.toString() + ", Interest Rate: " + interestRate + "%";
    }
}

class CurrentAccount extends BankAccount {
    private double overdraftLimit;

    public CurrentAccount(String accountNumber, String accountHolder, double overdraftLimit) {
        super(accountNumber, accountHolder);
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0) {
            if (balance + overdraftLimit >= amount) {
                balance -= amount;
                System.out.println("Withdrawal of $" + amount + " successful. Current balance: $" + balance);
            } else {
                System.out.println("Insufficient balance and overdraft limit. Withdrawal failed.");
            }
        } else {
            System.out.println("Invalid amount. Withdrawal failed.");
        }
    }

    @Override
    public String toString() {
        return "Current Account - " + super.toString() + ", Overdraft Limit: $" + overdraftLimit;
    }
}

public class BankingSystem {
    private List<BankAccount> accounts;

    public BankingSystem() {
        accounts = new ArrayList<>();
    }

    public void addAccount(BankAccount account) {
        accounts.add(account);
        System.out.println("Account added successfully.");
    }

    public void removeAccount(String accountNumber) {
        for (int i = 0; i < accounts.size(); i++) {
            BankAccount account = accounts.get(i);
            if (account.getAccountNumber().equals(accountNumber)) {
                accounts.remove(i);
                System.out.println("Account removed successfully.");
                return;
            }
        }
        System.out.println("Account not found.");
    }

    public void displayAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
        } else {
            System.out.println("Bank Accounts:");
            for (BankAccount account : accounts) {
                System.out.println(account);
            }
        }
    }

    public static void main(String[] args) {
        BankingSystem bankingSystem = new BankingSystem();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Banking System");
            System.out.println("1. Add Account");
            System.out.println("2. Remove Account");
            System.out.println("3. Display Accounts");
            System.out.println("4. Deposit");
            System.out.println("5. Withdraw");
            System.out.println("6. Apply Interest (Savings Account)");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter account number: ");
                    String accountNumber = scanner.nextLine();
                    System.out.print("Enter account holder: ");
                    String accountHolder = scanner.nextLine();
                    System.out.print("Enter account type (S for Savings, C for Current): ");
                    String accountType = scanner.nextLine();
                    if (accountType.equalsIgnoreCase("S")) {
                        System.out.print("Enter interest rate: ");
                        double interestRate = scanner.nextDouble();
                        BankAccount savingsAccount = new SavingsAccount(accountNumber, accountHolder, interestRate);
                        bankingSystem.addAccount(savingsAccount);
                    } else if (accountType.equalsIgnoreCase("C")) {
                        System.out.print("Enter overdraft limit: ");
                        double overdraftLimit = scanner.nextDouble();
                        BankAccount currentAccount = new CurrentAccount(accountNumber, accountHolder, overdraftLimit);
                        bankingSystem.addAccount(currentAccount);
                    } else {
                        System.out.println("Invalid account type.");
                    }
                    break;
                case 2:
                    System.out.print("Enter account number: ");
                    accountNumber = scanner.nextLine();
                    bankingSystem.removeAccount(accountNumber);
                    break;
                case 3:
                    bankingSystem.displayAccounts();
                    break;
                case 4:
                    System.out.print("Enter account number: ");
                    accountNumber = scanner.nextLine();
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    for (BankAccount account : bankingSystem.accounts) {
                        if (account.getAccountNumber().equals(accountNumber)) {
                            account.deposit(depositAmount);
                            break;
                        }
                    }
                    break;
                case 5:
                    System.out.print("Enter account number: ");
                    accountNumber = scanner.nextLine();
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    for (BankAccount account : bankingSystem.accounts) {
                        if (account.getAccountNumber().equals(accountNumber)) {
                            account.withdraw(withdrawAmount);
                            break;
                        }
                    }
                    break;
                case 6:
                    System.out.print("Enter account number: ");
                    accountNumber = scanner.nextLine();
                    for (BankAccount account : bankingSystem.accounts) {
                        if (account.getAccountNumber().equals(accountNumber) && account instanceof SavingsAccount) {
                            SavingsAccount savingsAccount = (SavingsAccount) account;
                            savingsAccount.applyInterest();
                            break;
                        }
                    }
                    break;
                case 7:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }

        scanner.close();
    }
}
