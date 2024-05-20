import java.util.HashMap;
import java.util.Map;

public class BankApp {
    private Map<String, BankAccount> accounts;
    private static final double TRANSFER_FEE_DIFFERENT_BANK = 5000;

    public BankApp() {
        this.accounts = new HashMap<>();
    }

    public void addAccount(BankAccount account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public BankAccount getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        BankAccount fromAccount = accounts.get(fromAccountNumber);
        BankAccount toAccount = accounts.get(toAccountNumber);

        if (fromAccount != null && toAccount != null) {
            double fee = fromAccount.getBankName().equals(toAccount.getBankName()) ? 0 : TRANSFER_FEE_DIFFERENT_BANK;

            if (fromAccount.getBalance() >= amount + fee) {
                fromAccount.withdraw(amount + fee);
                toAccount.deposit(amount);
                fromAccount.addTransaction(new Transaction("Transfer to " + toAccountNumber, amount));
                toAccount.addTransaction(new Transaction("Transfer from " + fromAccountNumber, amount));
                System.out.println("Transfer successful");
            } else {
                System.out.println("Insufficient funds for transfer");
            }
        } else {
            System.out.println("Invalid account number");
        }
    }

    public void printMonthlyStatement(String accountNumber, int month, int year) {
        BankAccount account = accounts.get(accountNumber);
        if (account != null) {
            System.out.println("Monthly statement for account: " + accountNumber);
            for (Transaction transaction : account.getTransactions()) {
                if (transaction.getDateTime().getMonthValue() == month && transaction.getDateTime().getYear() == year) {
                    System.out.println(transaction);
                }
            }
        } else {
            System.out.println("Account not found");
        }
    }

    public static void main(String[] args) {
        BankApp bankApp = new BankApp();

        // Create some accounts
        BankAccount account1 = new BankAccount("123", "Bank A", 100000);
        BankAccount account2 = new BankAccount("456", "Bank B", 50000);

        bankApp.addAccount(account1);
        bankApp.addAccount(account2);

        // Deposit and Withdraw
        account1.deposit(20000);
        account2.withdraw(10000);

        // Transfer
        bankApp.transfer("123", "456", 15000);

        // Print Monthly Statement
        bankApp.printMonthlyStatement("123", 5, 2024);
    }
}
