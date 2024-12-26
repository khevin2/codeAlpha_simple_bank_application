class Account{
    double balance;
    long accountNumber;
    String accountHolder;
    AccountType accountType;

    public Account(double balance, long accountNumber, String accountHolder, AccountType accountType){
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.accountType = accountType;
    }

    public Account(){}

    public void deposit(double amount){
        balance += amount;
    }

    public void withdraw(double amount)throws Exception{
        if(amount > balance) throw new Exception("Unsufficient balance to carry out this transaction");
        balance -= amount;
    }

    public double getBalance(){
        return balance;
    }

    public long getAccountNumber(){
        return accountNumber;
    }

    public String getAccountHolder(){
        return accountHolder;
    }

    public AccountType getAccountType(){
        return accountType;
    }

    public void setBalance(double balance){
        this.balance = balance;
    }

    public void setAccountNumber(long accountNumber){
        this.accountNumber = accountNumber;
    }

    public void setAccountHolder(String accountHolder){
        this.accountHolder = accountHolder;
    }

    public void setAccountType(AccountType accountType){
        this.accountType = accountType;
    }

    public String toString(){
        return "Account Number: " + accountNumber + "\nAccount Holder: " + accountHolder + "\nAccount Type: " + accountType + "\nBalance: " + balance;
    }
}

