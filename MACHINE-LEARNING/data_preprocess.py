import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt


# Load the CSV file
df = pd.read_csv('top_40_stocks_data.csv')
print(df.head())
print(df.info())
print(df.isnull().sum())
df = df.dropna()
print(df.dtypes)
df["Volume"]=df["Volume"].astype(float)
df["Date"]= df["Date"].astype("date32[pyarrow]")
print(df.dtypes)
numeric_data = df.select_dtypes(include=['float64', 'int64'])
correlation_matrix = numeric_data.corr()
print(correlation_matrix)
plt.figure(figsize=(10, 6))
sns.heatmap(correlation_matrix, annot=True, cmap="coolwarm", fmt=".2f", linewidths=0.5)
plt.title('Correlation Matrix of Stock Data', fontsize=14)
plt.show()
