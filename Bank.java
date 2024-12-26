import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Bank {
    public static void  main(String args[]) throws Exception{
        System.out.println("Welcome to Banking System");

        while (true) {

            System.out.println("Please select an option");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Create Account");
            System.out.println("5. Exit");

            int option = 0;

            try {
                option = Integer.parseInt(System.console().readLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid option selected\nPlease Select 1,2,3,4 or 5");
            }

            long accountNumber;
            Account account;

            double amount;
            switch (option) {
                case 1:
                    System.out.println("Please enter your account number");
                    accountNumber = Long.parseLong(System.console().readLine());
                    try {
                        account = getAccountByNumber(accountNumber);
                        System.out.println("Enter amount to deposit");
                        amount = Double.parseDouble(System.console().readLine());
                        account.deposit(amount);
                        updateAccount(account);
                        System.out.println("Amount deposited successfully");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Please enter your account number");
                    accountNumber = Long.parseLong(System.console().readLine());
                    try {
                        account = getAccountByNumber(accountNumber);
                        System.out.println("Enter amount to withdraw");
                        amount = Double.parseDouble(System.console().readLine());
                        account.withdraw(amount);
                        updateAccount(account);
                        System.out.println("Amount withdrawn successfully");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Please enter your account number");
                    accountNumber = Long.parseLong(System.console().readLine());
                    try {
                        account = getAccountByNumber(accountNumber);
                        System.out.println("Your account balance is: " + account.getBalance());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Enter account holder name");
                    String accountHolder = System.console().readLine();
                    System.out.println("Enter account type");
                    String accountType = System.console().readLine();
                    if(accountType.isEmpty()) accountType ="SAVINGS";
                    AccountType accountTypeEnum = AccountType.valueOf(accountType.toUpperCase());
                    accountNumber = System.currentTimeMillis();

                    Account newAccount = new Account(0.00, accountNumber, accountHolder, accountTypeEnum);
                    saveAccount(newAccount);
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option selected");
            }
        }
    }

    static Account getAccountByNumber(long accountNumber) throws IOException, Exception {
        // Code to get account by account number
        Scanner scanner = new Scanner(new File("bank_records.txt"));

        Account account = new Account();
        boolean found = false;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\t");
            if (Long.parseLong(parts[0]) == accountNumber) {
                found = true;
                account.setAccountNumber(Long.parseLong(parts[0]));
                account.setAccountHolder(parts[1]);
                account.setAccountType(AccountType.valueOf(parts[2].toUpperCase()));
                account.setBalance(Double.parseDouble(parts[3]));
                break;
            }
        }
        scanner.close();
        if(!found) throw new Exception("Account not found");
        return account;
    }
    
    static void saveAccount(Account account) throws IOException {
        // Code to save account to file
        BufferedWriter writer = new BufferedWriter(new FileWriter("bank_records.txt", true));

        writer.write(account.getAccountNumber() + "\t" + account.getAccountHolder() + "\t" + account.getAccountType()
                + "\t" + account.getBalance());
        writer.close();
    }
    
    static void updateAccount(Account account) throws IOException, Exception {
        // Code to update account in file
        Scanner scanner = new Scanner(new File("bank_records.txt"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("temp.txt"));
        boolean found = false;

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\t");
            if (Long.parseLong(parts[0]) == account.getAccountNumber()) {
                found = true;
                writer.write(
                        account.getAccountNumber() + "\t" + account.getAccountHolder() + "\t" + account.getAccountType()
                                + "\t" + account.getBalance());
            } else {
                writer.write(line);
            }
        }        
        scanner.close();
        writer.close();

        if(!found) throw new Exception("Account not found");

        File file = new File("bank_records.txt");
        file.delete();

        File tempFile = new File("temp.txt");
        tempFile.renameTo(file);
    }
}