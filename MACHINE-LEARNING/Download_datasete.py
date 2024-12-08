import yfinance as yf
import pandas as pd

# Define the list of top 40 stocks
top_40_stocks = [
    "AAPL", "MSFT", "GOOGL", "AMZN", "TSLA", "META", "NVDA", "JPM", "V", "UNH",
    "PG", "HD", "DIS", "MA", "VZ", "PFE", "KO", "MRK", "PEP", "INTC",
    "NFLX", "ADBE", "CSCO", "CMCSA", "NKE", "T", "WMT", "BAC", "XOM", "ABT",
    "ORCL", "CVX", "LLY", "IBM", "MCD", "AVGO", "HON", "RTX", "BA", "COST"
]

# Define the date range for one month
start_date = "2020-01-01"
end_date = "2020-02-01"

# Create a list to store the average data for each stock
average_data = []

# Loop over each stock and download the data
for ticker in top_40_stocks:
    print(f"Downloading data for {ticker}...")
    stock_data = yf.download(ticker, start=start_date, end=end_date)
    
    if not stock_data.empty:
        # Compute the average values for the specified columns
        average_values = stock_data[['Open', 'High', 'Low', 'Close', 'Adj Close', 'Volume']].mean()
        
        # Append a summary row
        average_data.append({
            "Ticker": ticker,
            "Open": average_values['Open'],
            "High": average_values['High'],
            "Low": average_values['Low'],
            "Close": average_values['Close'],
            "Adj Close": average_values['Adj Close'],
            "Volume": average_values['Volume']
        })

# Convert the average data to a DataFrame
average_df = pd.DataFrame(average_data)

# Save the average data to a CSV file
average_df.to_csv('top_40_stocks_average.csv', index=False)

print("Average stock data for one month has been saved to top_40_stocks_average.csv")
