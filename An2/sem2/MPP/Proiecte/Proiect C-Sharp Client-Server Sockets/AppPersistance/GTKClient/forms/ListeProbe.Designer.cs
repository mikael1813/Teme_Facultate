
namespace GTKClient.forms
{
    partial class ListeProbe
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.dataGridViewProbe = new System.Windows.Forms.DataGridView();
            this.dataGridViewParticipanti = new System.Windows.Forms.DataGridView();
            this.dataGridViewProbeParticipant = new System.Windows.Forms.DataGridView();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.button1 = new System.Windows.Forms.Button();
            this.button2 = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewProbe)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewParticipanti)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewProbeParticipant)).BeginInit();
            this.SuspendLayout();
            // 
            // dataGridViewProbe
            // 
            this.dataGridViewProbe.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewProbe.Location = new System.Drawing.Point(12, 125);
            this.dataGridViewProbe.Name = "dataGridViewProbe";
            this.dataGridViewProbe.RowHeadersWidth = 51;
            this.dataGridViewProbe.RowTemplate.Height = 24;
            this.dataGridViewProbe.Size = new System.Drawing.Size(456, 287);
            this.dataGridViewProbe.TabIndex = 0;
            this.dataGridViewProbe.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.probaSelected);
            // 
            // dataGridViewParticipanti
            // 
            this.dataGridViewParticipanti.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewParticipanti.Location = new System.Drawing.Point(565, 125);
            this.dataGridViewParticipanti.Name = "dataGridViewParticipanti";
            this.dataGridViewParticipanti.RowHeadersWidth = 51;
            this.dataGridViewParticipanti.RowTemplate.Height = 24;
            this.dataGridViewParticipanti.Size = new System.Drawing.Size(467, 287);
            this.dataGridViewParticipanti.TabIndex = 1;
            this.dataGridViewParticipanti.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.participantSelected);
            this.dataGridViewParticipanti.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridViewParticipanti_CellContentClick);
            // 
            // dataGridViewProbeParticipant
            // 
            this.dataGridViewProbeParticipant.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewProbeParticipant.Location = new System.Drawing.Point(1159, 125);
            this.dataGridViewProbeParticipant.Name = "dataGridViewProbeParticipant";
            this.dataGridViewProbeParticipant.RowHeadersWidth = 51;
            this.dataGridViewProbeParticipant.RowTemplate.Height = 24;
            this.dataGridViewProbeParticipant.Size = new System.Drawing.Size(489, 287);
            this.dataGridViewProbeParticipant.TabIndex = 2;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(169, 64);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(96, 17);
            this.label1.TabIndex = 3;
            this.label1.Text = "Lista Probelor";
            this.label1.Click += new System.EventHandler(this.label1_Click);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(781, 64);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(78, 17);
            this.label2.TabIndex = 4;
            this.label2.Text = "Participanti";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(1332, 64);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(150, 17);
            this.label3.TabIndex = 5;
            this.label3.Text = "Probele Participantului";
            this.label3.Click += new System.EventHandler(this.label3_Click);
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(393, 492);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 28);
            this.button1.TabIndex = 6;
            this.button1.Text = "Inscriere";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.inscreire);
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(1159, 492);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(76, 28);
            this.button2.TabIndex = 7;
            this.button2.Text = "Logout";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.logout);
            // 
            // ListeProbe
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1683, 532);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.dataGridViewProbeParticipant);
            this.Controls.Add(this.dataGridViewParticipanti);
            this.Controls.Add(this.dataGridViewProbe);
            this.Name = "ListeProbe";
            this.Text = "ListeProbe";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.Close);
            this.Load += new System.EventHandler(this.ListeProbe_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewProbe)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewParticipanti)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewProbeParticipant)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.DataGridView dataGridViewProbe;
        private System.Windows.Forms.DataGridView dataGridViewParticipanti;
        private System.Windows.Forms.DataGridView dataGridViewProbeParticipant;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button button2;
    }
}