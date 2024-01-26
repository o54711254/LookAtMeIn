import os
from paramiko import SSHClient, RSAKey, SFTPClient, AutoAddPolicy, SSHException;
import socket

class Sftp:
    client: SSHClient = SSHClient()
    sftp: SFTPClient

    def __init__(self, host:str, user:str, key_file:str):
        if not os.path.isfile(key_file):
            raise FileNotFoundError(f"Key file {key_file} not found")
        
        key = RSAKey.from_private_key_file(key_file)
        self.client.set_missing_host_key_policy(AutoAddPolicy())
        try:
            self.client.connect(host, username=user, pkey=key)
        except socket.error:
            raise SSHException(f"Could not connect to {host}")
        
        self.sftp = self.client.open_sftp()

    def upload(self, local_path, remote_path):
        if not os.path.exists(local_path):
            self.client.close();
            raise FileNotFoundError(f"Local file {local_path} not found")
        print("local_path: ", local_path, "remote_path: ", remote_path)
        self.sftp.put(local_path, remote_path) # file upload
        self.client.close()

    
    def download(self, local_path, remote_path):
        try:
            self.sftp.get(remote_path, local_path) # file download
        except IOError as e:
            raise e("Failed to find remote file")
        finally:
            self.client.close()


# from dotenv import load_dotenv

# load_dotenv()

# sftp_host = os.getenv("SFTP_HOST")  
# sftp_user = os.getenv("SFTP_USER")
# sftp_key_file = os.getenv("SFTP_KEY_FILE_PATH")
# sftp = Sftp(host=sftp_host, user=sftp_user, key_file=sftp_key_file)
# # print(sftp_key_file)
# sftp.upload("./output.png", "/home/ubuntu/output.png")