import yfinance as yf
import pandas as pd

# Define the list of top 40 stocks
top_40_stocks = [
    "AAPL", "MSFT", "GOOGL", "AMZN", "TSLA", "META", "NVDA", "JPM", "V", "UNH",
    "PG", "HD", "DIS", "MA", "VZ", "PFE", "KO", "MRK", "PEP", "INTC",
    "NFLX", "ADBE", "CSCO", "CMCSA", "NKE", "T", "WMT", "BAC", "XOM", "ABT",
    "ORCL", "CVX", "LLY", "IBM", "MCD", "AVGO", "HON", "RTX", "BA", "COST"
]

# Define the date range
start_date = "2020-01-01"
end_date = "2023-01-01"

# Create an empty DataFrame to hold all the stock data
all_stock_data = pd.DataFrame()

# Loop over each stock and download the data
for ticker in top_40_stocks:
    print(f"Downloading data for {ticker}...")
    stock_data = yf.download(ticker, start=start_date, end=end_date)
    
    # Add a column to the stock data to identify which stock the data belongs to
    stock_data['Ticker'] = ticker
    
    # Append this stock's data to the main DataFrame
    all_stock_data = pd.concat([all_stock_data, stock_data])

# Save the combined data to a single CSV file
all_stock_data.to_csv('top_40_stocks_data.csv')

print("All stock data has been saved to top_40_stocks_data.csv")
