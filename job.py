import requests
import time

BASE_URL = "http://localhost:8081"  # Nome do serviço Docker

REGISTER_ENDPOINT = f"{BASE_URL}/auth/registrar"
LOGIN_ENDPOINT = f"{BASE_URL}/auth/login"
USERS_ENDPOINT = f"{BASE_URL}/users/1"

USER_CREDENTIALS = {
    "nome": "Thais2",
    "nickname": "tatitata12345",
    "senha" : "123",
    "idade": 24
}

for _ in range(300):  # tenta por até ~30 segundos
    try:
        res = requests.get(BASE_URL)
        if res.status_code < 500:
            print("API disponível!")
            break
    except Exception as e:
        print("Aguardando API subir...")
        time.sleep(1)
else:
    print("API não respondeu a tempo.")
    exit(1)


# 1. Registro
res = requests.post(REGISTER_ENDPOINT, json=USER_CREDENTIALS)
print(f"Registro: {res.status_code} - {res.text}")

# 2. Login
res = requests.post(LOGIN_ENDPOINT, json=USER_CREDENTIALS)
token = res.json().get("token")
print(f"Token recebido: {token[:30]}...")

# 3. Chamadas GET
headers = {"Authorization": f"{token}"}
start_time = time.time()

for i in range(1000):
    r = requests.get(USERS_ENDPOINT, headers=headers)
    if r.status_code != 200:
        print(f"Erro na chamada {i}: {r.status_code} - {r.text}")
        break

end_time = time.time()
print(f"Tempo total para 1000 requisições: {end_time - start_time:.2f} segundos")
