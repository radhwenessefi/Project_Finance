import pandas as pd
import pandas_ta as ta
import requests 
from backtesting import Strategy 
from backtesting import Backtest
import json
from flask import Flask, jsonify, request
from backtesting.lib import crossover



    
app = Flask(__name__)

@app.route('/get_stat', methods=['GET'])
def get_stat():

 
 symbol = request.args.get('jsonData', default='IBM', type=str)
 cash = request.args.get('cash', default=10000, type=int)
 margin = request.args.get('margin', default=1/2, type=float)
 stratNum = request.args.get('stratNum', default=1, type=int)

 key = '7D4D2IVSL1NS5WR3'
 print(symbol,cash,margin,stratNum)
 url = 'https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol='+symbol+'&outputsize=full&apikey='+key+'&datatype=csv'
 
 df = pd.read_csv(url)  
 print('bbb',df)

 df = pd.DataFrame(df)
  

 #df['RSI'] = ta.rsi(df.close, length=14)
 df['RSI'] = ta.rsi(df.close, length=14)
 df['EMA_slow'] = ta.ema(df.close, length=200)#sma slow moving average
 df['EMA_fast'] = ta.ema(df.close, length=150)#sma slow moving average

 my_bbands = ta.bbands(df.close, length=14, std=2.0)
 my_bbands[0:50]
 df=df.join(my_bbands)
 df.dropna(inplace=True)
 df.reset_index(inplace=True)

 def addemasignal(df):
    emasignal = [0]*len(df)
    for i in range(0, len(df)):
        if df.EMA_slow[i]>df.EMA_fast[i]:
            emasignal[i]=2
        elif df.EMA_slow[i]<df.EMA_fast[i]:
            emasignal[i]=1
    df['EMASignal'] = emasignal
 addemasignal(df)  

 def addorderslimit(df, percent):
    ordersignal=[0]*len(df)
    for i in range(1, len(df)): #EMASignal of previous candle 
        if df.close[i]<=df['BBL_14_2.0'][i] and df.EMASignal[i]==2:
            ordersignal[i]=df.close[i]-df.close[i]*percent
        elif df.close[i]>=df['BBU_14_2.0'][i] and df.EMASignal[i]==1:
            ordersignal[i]=df.close[i]+df.close[i]*percent
    df['ordersignal']=ordersignal
 addorderslimit(df,0.000)    

 dfcopy = df[:].copy()
 
 if  stratNum==1:
     
  def SIGNAL():
     return dfcopy.ordersignal

  dfcopy = dfcopy.rename(columns = {'open': 'Open', 'high': 'High', 'low': 'Low', 'close': 'Close', 'volume': 'Volume'})


 
  class MyStrat (Strategy):
     initsize = 0.99
     mysize = initsize
     def init(self):
         super().init()
         self.signal = self.I(SIGNAL)
     def next(self):
         super().next()
         TPSLRatio = 2
         perc = 0.02

         if len(self.trades)>0:
             if self.data.index[-1]-self.trades[-1].entry_time>=10:
                self.trades[-1].close()
             if self.trades[-1].is_long and self.data.RSI[-1]>=75:
                self.trades[-1].close()
             elif self.trades[-1].is_short and self.data.RSI[-1]<=25:
                self.trades[-1].close()
                
                
         if self.signal!=0 and len(self.trades)==0 and self.data.EMASignal==2:
            sl1 = min(self.data.Low[-1],self.data.Low[-2])*(1-perc)
            tp1 = self.data.Close[-1]+(self.data.Close[-1] - sl1) *TPSLRatio
            self.buy(sl=sl1, tp=tp1, size=self.mysize)
            
         elif self.signal!=0 and len(self.trades)== 0 and self.data.EMASignal==1:
            sl1 = sl1 = max(self.data.High[-1],self.data.High[-2])*(1+perc)
            tp1 = self.data.Close[-1]-(sl1 - self.data.Close[-1])*TPSLRatio
            self.sell(sl=sl1, tp=tp1, size=self.mysize)

  bt = Backtest(dfcopy, MyStrat, cash=cash, margin=margin , commission=.000)
  stat = bt.run()

            
 elif stratNum == 2 :   

    def add_ma_crossover_signal(df):
       df['SMA_50'] = ta.sma(df['close'], window=50)  # 50-day Simple Moving Average
       df['SMA_200'] = ta.sma(df['close'], window=200)  # 200-day Simple Moving Average
       df['MA_Crossover'] = ta.utils.cross(df['SMA_50'], df['SMA_200'])

    def signal_ma_crossover(df):
       add_ma_crossover_signal(df)
       return df['MA_Crossover']  
     
    add_ma_crossover_signal(df)
    dfcopy = df[:].copy()
    def SIGNAL():
      return dfcopy.SMA_200
    
    dfcopy = dfcopy.rename(columns = {'open': 'Open', 'high': 'High', 'low': 'Low', 'close': 'Close', 'volume': 'Volume'})

    class MovingAverageStrategy(Strategy):
        initsize = 0.99
        mysize = initsize

        def init(self):
            super().init()
            self.signal = self.I(SIGNAL)

        def next(self):
            super().next()
            TPSLRatio = 2
            perc = 0.02

            if len(self.trades) > 0:
            # Place conditions to close trades
            # For example, if the price goes against the position or based on a specific indicator
                 pass


            if self.signal > 0 and len(self.trades) == 0:
            # Buy signal - Place buy trade
                sl1 = self.data.Close[-1] * (1 - perc)
                tp1 = self.data.Close[-1] + (self.data.Close[-1] - sl1) * TPSLRatio
                self.buy(sl=sl1, tp=tp1, size=self.mysize)

            elif self.signal < 0 and len(self.trades) == 0:
            # Sell signal - Place sell trade
                sl1 = self.data.Close[-1] * (1 + perc)
                tp1 = self.data.Close[-1] - (sl1 - self.data.Close[-1]) * TPSLRatio
                self.sell(sl=sl1, tp=tp1, size=self.mysize)

    bt = Backtest(dfcopy, MovingAverageStrategy, cash=cash, margin=margin , commission=.000)
    stat = bt.run()   

 elif stratNum==3:
     dfcopy = dfcopy.rename(columns={dfcopy.columns[11]:'BBM_14'})
     def SIGNAL():
       return dfcopy.BBM_14
     dfcopy = dfcopy.rename(columns = {'open': 'Open', 'high': 'High', 'low': 'Low', 'close': 'Close', 'volume': 'Volume'})
     # Assume 'dfcopy' is your DataFrame with price data (e.g., Close prices)
     window = 20  # Window size for moving average
     std_multiplier = 2  # Standard deviation multiplier for bands

     # Calculate rolling mean and standard deviation
     dfcopy['BB_MA'] = dfcopy['Close'].rolling(window=window).mean()
     dfcopy['BB_std'] = dfcopy['Close'].rolling(window=window).std()

     # Calculate upper and lower bands
     dfcopy['BB_upper'] = dfcopy['BB_MA'] + (dfcopy['BB_std'] * std_multiplier)
     dfcopy['BB_lower'] = dfcopy['BB_MA'] - (dfcopy['BB_std'] * std_multiplier)

     for i in range(1, len(dfcopy)):
          dfcopy['BB_Mid'] = ( dfcopy['BB_upper'] + dfcopy['BB_lower'] ) / 2

     def SIGNAL():
         return dfcopy.BB_Mid
     
     class Bollinger_Bands_Strat(Strategy):
            initsize = 0.02  # Adjust initial trade size (percentage of equity)
            mysize = initsize

            def init(self):
                super().init()
                self.signal = self.I(SIGNAL)
                self.position_open = False  # Track if a position is open

            def next(self):
                super().next()
                TPSLRatio = 2
                perc = 0.5

                if len(self.trades) > 0 and self.position_open:
                 # Check if the price goes against the position or based on a specific indicator
                    if self.signal < 0 and self.data.Close[-1] > self.data.Close[-2]:
                        self.close()  # Close the position
                        self.position_open = False  # Reset position flag

            # Add more conditions based on other indicators or price movements as needed

                if self.signal > 0 and len(self.trades) == 0:
            # Buy signal - Place buy trade with adjusted position size
                    trade_size = 0.1  # Example: Set trade size as a fraction of equity
                    self.buy(size=trade_size)  # Adjust other parameters accordingly
                    self.position_open = True  # Set position flag when a trade is opened

                elif self.signal < 0 and len(self.trades) == 0:
                # Sell signal - Place sell trade with adjusted position size
                    trade_size = 0.1  # Example: Set trade size as a fraction of equity
                    self.sell(size=trade_size)  # Adjust other parameters accordingly
                    self.position_open = True  # Set position flag when a trade is opened

     bt = Backtest(dfcopy, Bollinger_Bands_Strat , cash=cash, margin=margin , commission=.000)
     stat = bt.run() 




 stat_dict = {
    "Start": stat['Start'],
    "End": stat['End'],
    "Duration": stat['Duration'],
    "Exposure Time [%]": stat['Exposure Time [%]'],
    "Equity Final [$]": stat['Equity Final [$]'],
    "Equity Peak [$]": stat['Equity Peak [$]'],
    "Return [%]": stat['Return [%]'],
    "Buy & Hold Return [%]": stat['Buy & Hold Return [%]'],
    "Return (Ann.) [%]": stat['Return (Ann.) [%]'],
    "Calmar Ratio": stat['Calmar Ratio'],
    "Max. Drawdown [%]": stat['Max. Drawdown [%]'],
    "Avg. Drawdown [%]": stat['Avg. Drawdown [%]'],
    "Max. Drawdown Duration": stat['Max. Drawdown Duration'],
    "Avg. Drawdown Duration": stat['Avg. Drawdown Duration'],
    "# Trades": stat['# Trades'],
    "Win Rate [%]": stat['Win Rate [%]'],
    "Best Trade [%]": stat['Best Trade [%]'],
    "Worst Trade [%]": stat['Worst Trade [%]'],
    "Avg. Trade [%]": stat['Avg. Trade [%]'],
    "Max. Trade Duration": stat['Max. Trade Duration'],
    "Avg. Trade Duration": stat['Avg. Trade Duration'],
    "Profit Factor": stat['Profit Factor'],
    "Expectancy [%]": stat['Expectancy [%]'],
    "SQN": stat['SQN'],
    
 }

 # Save the 'stat_dict' as a JSON file
 with open('stat.json', 'w') as file:
    json.dump(stat_dict, file)


 def load_stat_from_file():
        
        with open('stat.json', 'r') as file:
            stat_dict = json.load(file)
        return stat_dict

        
 stat = load_stat_from_file()
    
 return jsonify(stat)

if __name__ == '__main__':
    app.run(debug=True)
   