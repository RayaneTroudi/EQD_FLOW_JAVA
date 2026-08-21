import pandas as pd
df = pd.read_parquet('spy_eod_2010.parquet')
df.to_csv('spy_eod_2010.csv', index=False)